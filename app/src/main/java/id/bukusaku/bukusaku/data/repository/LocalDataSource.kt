package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.local.dao.*
import id.bukusaku.bukusaku.data.local.entity.ArticlesEntity
import id.bukusaku.bukusaku.data.local.entity.CategoriesEntity
import id.bukusaku.bukusaku.data.local.entity.NewArticleEntity
import id.bukusaku.bukusaku.data.local.entity.ProductEntity
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.ProductDetail
import io.reactivex.Single
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LocalDataSource(
    private val categoriesDao: CategoriesDao, private val newArticlesDao: NewArticlesDao,
    private val articlesDao: ArticlesDao, private val productsDao: ProductsDao,
    private val bookmarkProduct: BookmarkProductDao, private val bookmarkArticle: BookmarkArticleDao
) {
    fun getLocalCategories(): Single<List<CategoriesEntity>> = categoriesDao.getLocalCategories()

    fun getLocalNewArticle(): Single<List<NewArticleEntity>> = newArticlesDao.getLocalNewArticles()

    fun getLocalArticle(): Single<List<ArticlesEntity>> = articlesDao.getLocalArticles()

    fun getLocalProducts(categoryName: String): Single<List<ProductEntity>> = productsDao.getLocalProducts(categoryName)

    fun saveCategories(data: List<CategoriesEntity>) = categoriesDao.insertCategories(data)

    fun saveNewArticles(data: List<NewArticleEntity>) = newArticlesDao.insertNewArticles(data)

    fun saveArticles(data: List<ArticlesEntity>) = articlesDao.insertArticles(data)

    fun saveProducts(data: List<ProductEntity>) = productsDao.insertProducts(data)

    suspend fun bookmarkProduct(data: ProductDetail) =
        GlobalScope.async { bookmarkProduct.insertBookmark(data) }.await()

    suspend fun getDataBookmark(id: Int): ProductDetail =
        GlobalScope.async { bookmarkProduct.getProductBookmark(id) }.await()

    suspend fun deleteBookmark(id: Int) = GlobalScope.async { bookmarkProduct.deleteBookmark(id) }.await()

    suspend fun getAllBookmarkProduct(): List<ProductDetail> =
        GlobalScope.async { bookmarkProduct.getAllProductBookmark() }.await()

    suspend fun bookmarkArticle(data: ArticleDetail) =
        GlobalScope.async { bookmarkArticle.insertBookmark(data) }.await()

    suspend fun getBookmarkArticleById(id: Int): ArticleDetail =
        GlobalScope.async { bookmarkArticle.getArticleBookmark(id) }.await()

    suspend fun getAllBookmarkArticle(): List<ArticleDetail> =
        GlobalScope.async { bookmarkArticle.getAllBookmark() }.await()

    suspend fun deleteBookmarkArticle(id: Int) = GlobalScope.async { bookmarkArticle.deleteBookmark(id) }.await()

    suspend fun getArticleSearchResult(query: String?): List<ArticlesEntity> =
        GlobalScope.async { articlesDao.getSearchResult(query) }.await()

    suspend fun getProductSearchResult(query: String?, categoryName: String?): List<ProductEntity> =
        GlobalScope.async { productsDao.getSearchResult(query, categoryName) }.await()

    suspend fun getSearchArticlesBookmarkResult(query: String?): List<ArticleDetail> =
        GlobalScope.async { bookmarkArticle.getSearchBookmarkResult(query) }.await()

    suspend fun getSearchProductsBookmarkResult(query: String?): List<ProductDetail> =
        GlobalScope.async { bookmarkProduct.getSearchBookmarkResult(query) }.await()
}