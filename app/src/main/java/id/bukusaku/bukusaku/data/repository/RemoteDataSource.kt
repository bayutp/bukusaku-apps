package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.remote.Enquiry
import id.bukusaku.bukusaku.data.response.*
import id.bukusaku.bukusaku.network.ApiService
import io.reactivex.Single

class RemoteDataSource(private val apiService: ApiService) {
    fun getNewArticles(): Single<ArticlesResponse> = apiService.getNewArticles(5, 1)

    fun getArticles(): Single<ArticleDetailResponse> = apiService.getArticles()

    fun getCategories(): Single<CategoriesResponse> = apiService.getCategories()

    fun getArticleById(id: Int): Single<ArticleDetailResponseById> = apiService.getArticleById(id)

    fun getProducts(categoryName: String): Single<ProductsResponse> = apiService.getProducts(categoryName)

    fun getProductById(id: Int): Single<ProductDetailResponse> = apiService.getProductById(id)

    fun sendEnquiry(enquiry: Enquiry): Single<EnquiryResponse> = apiService.sendEnquiry(enquiry)

    fun getSearchProductResponse(productName: String?): Single<SearchProductResponse> =
        apiService.getSearchProductResult(productName)
}