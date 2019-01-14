package id.bukusaku.bukusaku.network

import id.bukusaku.bukusaku.data.remote.Enquiry
import id.bukusaku.bukusaku.data.response.*
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {
    @GET("categories")
    fun getCategories(): Single<CategoriesResponse>

    @GET("articles")
    fun getNewArticles(@Query("perpage") lastPage: Int, @Query("page") page: Int): Single<ArticlesResponse>

    @GET("articles")
    fun getArticles(): Single<ArticleDetailResponse>

    @GET("articles/{id}")
    fun getArticleById(@Path("id") id: Int): Single<ArticleDetailResponseById>

    @GET("products/category/{category_name}")
    fun getProducts(@Path("category_name") categoryName: String): Single<ProductsResponse>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Single<ProductDetailResponse>

    @POST("enquiries")
    fun sendEnquiry(@Body enquiry: Enquiry): Single<EnquiryResponse>

    @GET("products/search/{product_name}")
    fun getSearchProductResult(@Path("product_name") productName: String?): Single<SearchProductResponse>

}