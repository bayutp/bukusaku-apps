package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.response.*
import id.bukusaku.bukusaku.network.ApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteDataSource(val apiService: ApiService) {
    fun getNewArticles(): Single<ArticlesResponse> {
        return apiService.getNewArticles(5, 1)
    }

    fun getArticles(): Single<ArticleDetailResponse> {
        return apiService.getArticles()
    }

    fun getCategories(): Single<CategoriesResponse> {
        return apiService.getCategories()
    }

    fun getArticleById(id:Int):Single<ArticleDetailResponseById>{
        return apiService.getArticleById(id)
    }

    fun getProducts(categoryName:String):Single<ProductsResponse>{
        return apiService.getProducts(categoryName)
    }

    fun getProductById(id:Int):Single<ProductDetailResponse>{
        return apiService.getProductById(id)
    }

}