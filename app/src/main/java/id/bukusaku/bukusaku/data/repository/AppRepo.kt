package id.bukusaku.bukusaku.data.repository

import android.annotation.SuppressLint
import android.util.Log
import id.bukusaku.bukusaku.data.local.entity.ArticlesEntity
import id.bukusaku.bukusaku.data.local.entity.CategoriesEntity
import id.bukusaku.bukusaku.data.local.entity.NewArticleEntity
import id.bukusaku.bukusaku.data.local.entity.ProductEntity
import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.Enquiry
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.data.response.ArticleDetailResponseById
import id.bukusaku.bukusaku.data.response.EnquiryResponse
import id.bukusaku.bukusaku.data.response.ProductDetailResponse
import id.bukusaku.bukusaku.data.response.SearchProductResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class AppRepo(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) :
    DataSource {

    @SuppressLint("LogNotTimber")
    override fun getCategories(): Single<List<Categories>> {
        return localDataSource.getLocalCategories()
            .map { list -> list.map { Categories.from(it) } }
            .flatMap {
                if (it.isEmpty()) getCategoriesFromRemote() else Single.just(it)
            }
            .doAfterSuccess {
                getCategoriesFromRemote().subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.d(AppRepo::class.java.simpleName, "refresh categories") },
                        { error -> Log.e(AppRepo::class.java.simpleName, error.localizedMessage) }
                    )
            }
    }

    @SuppressLint("LogNotTimber")
    override fun getNewArticles(): Single<List<NewArticles>> {
        return localDataSource.getLocalNewArticle()
            .map { listEntity ->
                listEntity.map { NewArticles.from(it) }
            }
            .flatMap { listNew ->
                if (listNew.isEmpty()) getNewArticlesFromRemote() else Single.just(listNew)
            }
            .doAfterSuccess {
                getNewArticlesFromRemote().subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.d(AppRepo::class.java.simpleName, "refresh new articles") },
                        { error -> Log.e(AppRepo::class.java.simpleName, error.localizedMessage) }
                    )
            }
    }

    @SuppressLint("LogNotTimber")
    override fun getArticles(): Single<List<Articles>> {
        return localDataSource.getLocalArticle()
            .map { listEntity -> listEntity.map { Articles.from(it) } }
            .flatMap { listArticles ->
                if (listArticles.isEmpty()) getArticlesFromRemote() else Single.just(listArticles)
            }
            .doAfterSuccess {
                getArticlesFromRemote().subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.d(AppRepo::class.java.simpleName, "refresh articles") },
                        { error -> Log.e(AppRepo::class.java.simpleName, error.localizedMessage) }
                    )
            }
    }

    override fun getArticleById(id: Int): Single<ArticleDetailResponseById> = remoteDataSource.getArticleById(id)

    override fun getProductById(id: Int): Single<ProductDetailResponse> = remoteDataSource.getProductById(id)

    override fun sendEnquiry(enquiry: Enquiry): Single<EnquiryResponse> = remoteDataSource.sendEnquiry(enquiry)

    private fun getCategoriesFromRemote(): Single<List<Categories>> {
        return remoteDataSource.getCategories()
            .doOnSuccess {
                localDataSource.saveCategories(
                    it.data.map { categories -> CategoriesEntity.from(categories) })
            }
            .map { list -> list.data.map { Categories.from(it) } }
    }

    private fun getNewArticlesFromRemote(): Single<List<NewArticles>> {
        return remoteDataSource.getNewArticles()
            .doOnSuccess {
                localDataSource.saveNewArticles(
                    it.data.map { newArticles -> NewArticleEntity.from(newArticles) })
            }
            .map { response -> response.data.map { NewArticles.from(it) } }
    }

    private fun getArticlesFromRemote(): Single<List<Articles>> {
        return remoteDataSource.getArticles()
            .doOnSuccess {
                localDataSource.saveArticles(
                    it.data.map { articles -> ArticlesEntity.from(articles) })
            }
            .map { response -> response.data.map { Articles.from(it) } }
    }

    @SuppressLint("LogNotTimber")
    override fun getProducts(categoryName: String): Single<List<ProductsMap>> {
        return localDataSource.getLocalProducts(categoryName)
            .map { productsLocal -> productsLocal.map { ProductsMap.from(it) } }
            .flatMap { listProducts ->
                if (listProducts.isEmpty()) getProductsFromRemote(categoryName)
                else Single.just(listProducts)
            }
            .doAfterSuccess {
                getProductsFromRemote(categoryName)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.d(AppRepo::class.java.simpleName, "update products") },
                        { error -> Log.d(AppRepo::class.java.simpleName, error.localizedMessage) })
            }
    }

    private fun getProductsFromRemote(namaCategory: String): Single<List<ProductsMap>> {
        return remoteDataSource.getProducts(namaCategory)
            .doOnSuccess {
                localDataSource.saveProducts(it.data.map { products -> ProductEntity.from(products) })
            }
            .map { productResponse -> productResponse.data.map { ProductsMap.from(it) } }

    }

    override suspend fun bookmarkProduct(data: ProductDetail) {
        localDataSource.bookmarkProduct(data)
    }

    override suspend fun deleteBookmark(id: Int) {
        localDataSource.deleteBookmark(id)
    }

    override suspend fun bookmarkArticle(data: ArticleDetail) {
        localDataSource.bookmarkArticle(data)
    }

    override suspend fun deleteBookmarkArticle(id: Int) {
        localDataSource.deleteBookmarkArticle(id)
    }

    override suspend fun getBookmarkProduct(id: Int): ProductDetail = localDataSource.getDataBookmark(id)

    override suspend fun getAllBookmarkProduct(): List<ProductDetail> = localDataSource.getAllBookmarkProduct()

    override suspend fun getBookmarkArticleById(id: Int): ArticleDetail = localDataSource.getBookmarkArticleById(id)

    override suspend fun getAllBookmarkArticle(): List<ArticleDetail> = localDataSource.getAllBookmarkArticle()

    override fun getSearchProductResult(productName: String?): Single<SearchProductResponse> =
        remoteDataSource.getSearchProductResponse(productName)

    override suspend fun getArticlesSearchResult(query: String?): List<Articles> =
        localDataSource.getArticleSearchResult(query).map { Articles.from(it) }

    override suspend fun getProductsSearchResult(query: String?, categoryName: String?): List<ProductsMap> =
        localDataSource.getProductSearchResult(query, categoryName).map { ProductsMap.from(it) }

    override suspend fun getArticlesBookmarkSearchResult(query: String?): List<ArticleDetail> =
        localDataSource.getSearchArticlesBookmarkResult(query)

    override suspend fun getProductsBookmarkSearchResult(query: String?): List<ProductDetail> =
        localDataSource.getSearchProductsBookmarkResult(query)
}