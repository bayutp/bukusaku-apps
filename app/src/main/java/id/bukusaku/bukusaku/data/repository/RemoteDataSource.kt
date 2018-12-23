package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.response.ArticleDetailResponse
import id.bukusaku.bukusaku.data.response.ArticlesResponse
import id.bukusaku.bukusaku.data.response.CategoriesResponse
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

}