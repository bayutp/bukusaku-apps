package id.bukusaku.bukusaku.data.repository

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

interface DataSource {
    fun getCategories(): Single<List<Categories>>

    fun getNewArticles(): Single<List<NewArticles>>

    fun getArticles(): Single<List<Articles>>

    fun getArticleById(id: Int): Single<ArticleDetailResponseById>

    fun getProducts(categoryName: String): Single<List<ProductsMap>>

    fun getProductById(id: Int): Single<ProductDetailResponse>

    fun sendEnquiry(enquiry: Enquiry): Single<EnquiryResponse>

    suspend fun bookmarkProduct(data: ProductDetail)

    suspend fun getBookmarkProduct(id: Int): ProductDetail

    suspend fun deleteBookmark(id: Int)

    suspend fun getAllBookmarkProduct(): List<ProductDetail>

    suspend fun bookmarkArticle(data: ArticleDetail)

    suspend fun getBookmarkArticleById(id: Int): ArticleDetail

    suspend fun getAllBookmarkArticle(): List<ArticleDetail>

    suspend fun deleteBookmarkArticle(id: Int)

    fun getSearchProductResult(productName: String?): Single<SearchProductResponse>

    suspend fun getArticlesSearchResult(query: String?): List<Articles>

    suspend fun getProductsSearchResult(query: String?, categoryName: String?): List<ProductsMap>

    suspend fun getArticlesBookmarkSearchResult(query: String?): List<ArticleDetail>

    suspend fun getProductsBookmarkSearchResult(query: String?): List<ProductDetail>
}