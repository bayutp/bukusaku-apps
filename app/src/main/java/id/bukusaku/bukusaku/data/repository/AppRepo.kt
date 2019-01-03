package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.local.entity.ArticlesEntity
import id.bukusaku.bukusaku.data.local.entity.CategoriesEntity
import id.bukusaku.bukusaku.data.local.entity.NewArticleEntity
import id.bukusaku.bukusaku.data.local.entity.ProductEntity
import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.data.response.ArticleDetailResponseById
import id.bukusaku.bukusaku.data.response.ProductDetailResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AppRepo(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DataSource {

    override fun getCategories(): Single<List<Categories>> {
        return localDataSource.getLocalCategories()
            .map { list ->
                list.map { Categories.from(it) }
            }
            .flatMap {
                if (it.isEmpty()) getCategoriesFromRemote() else Single.just(it)
            }
            .doAfterSuccess {
                getCategoriesFromRemote().subscribeOn(Schedulers.io())
                    .subscribe({
                        Timber.d("refresh categories")
                    }, { error ->
                        Timber.e(error.localizedMessage)
                    })
            }
    }

    override fun getNewArticles(): Single<List<NewArticles>> {
        return localDataSource.getLocalNewArticle()
            .map { listEntity ->
                listEntity.map {
                    NewArticles.from(it)
                }
            }
            .flatMap { listNew ->
                if (listNew.isEmpty()) getNewArticlesFromRemote() else Single.just(listNew)
            }
            .doAfterSuccess {
                getNewArticlesFromRemote().subscribeOn(Schedulers.io())
                    .subscribe({
                        Timber.d("refresh new articles")
                    }, { error ->
                        Timber.e(error)
                    })
            }
    }

    override fun getArticles(): Single<List<Articles>> {
        return localDataSource.getLocalArticle()
            .map { listEntity ->
                listEntity.map {
                    Articles.from(it)
                }
            }
            .flatMap { listArticles ->
                if (listArticles.isEmpty()) getArticlesFromRemote() else Single.just(listArticles)
            }
            .doAfterSuccess {
                getArticlesFromRemote().subscribeOn(Schedulers.io())
                    .subscribe({
                        Timber.d("refresh articles")
                    }, { error ->
                        Timber.e(error)
                    })
            }
    }

    override fun getArticleById(id: Int): Single<ArticleDetailResponseById> {
        return remoteDataSource.getArticleById(id)
    }

    override fun getProductById(id: Int): Single<ProductDetailResponse> {
        return remoteDataSource.getProductById(id)
    }

    private fun getCategoriesFromRemote(): Single<List<Categories>> {
        return remoteDataSource.getCategories()
            .doOnSuccess {
                localDataSource.saveCategories(it.data.map { categories ->
                    CategoriesEntity.from(categories)
                })
            }
            .map { list ->
                list.data.map { Categories.from(it) }
            }
    }

    private fun getNewArticlesFromRemote(): Single<List<NewArticles>> {
        return remoteDataSource.getNewArticles()
            .doOnSuccess {
                localDataSource.saveNewArticles(
                    it.data.map { newArticles ->
                        NewArticleEntity.from(newArticles)
                    }
                )
            }
            .map { response ->
                response.data.map { NewArticles.from(it) }
            }
    }

    private fun getArticlesFromRemote(): Single<List<Articles>> {
        return remoteDataSource.getArticles()
            .doOnSuccess {
                localDataSource.saveArticles(
                    it.data.map { articles ->
                        ArticlesEntity.from(articles)
                    }
                )
            }
            .map { response ->
                response.data.map { Articles.from(it) }
            }
    }

    override fun getProducts(categoryName: String): Single<List<ProductsMap>> {
        return localDataSource.getLocalProducts(categoryName)
            .map { productsLocal ->
                productsLocal.map {
                    ProductsMap.from(it)
                }
            }
            .flatMap { listProducts ->
                if (listProducts.isEmpty()) getProductsFromRemote(categoryName)
                else Single.just(listProducts)
            }
            .doAfterSuccess {
                getProductsFromRemote(categoryName)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        Timber.d("update products")
                    }, { error ->
                        Timber.e(error.localizedMessage)
                    })
            }
    }

    private fun getProductsFromRemote(namaCategory: String): Single<List<ProductsMap>> {
        return remoteDataSource.getProducts(namaCategory)
            .doOnSuccess {
                localDataSource.saveProducts(
                    it.data.map { products ->
                        ProductEntity.from(products)
                    }
                )
            }
            .map { productResponse ->
                productResponse.data.map {
                    ProductsMap.from(it)
                }
            }

    }

}