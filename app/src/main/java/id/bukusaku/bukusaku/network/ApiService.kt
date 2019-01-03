package id.bukusaku.bukusaku.network

import id.bukusaku.bukusaku.data.response.ArticleDetailResponse
import id.bukusaku.bukusaku.data.response.ArticlesResponse
import id.bukusaku.bukusaku.data.response.CategoriesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories")
    fun getCategories(): Single<CategoriesResponse>

    @GET("articles")
    fun getNewArticles(@Query("perpage") lastPage:Int, @Query("page") page:Int): Single<ArticlesResponse>

    @GET("articles")
    fun getArticles():Single<ArticleDetailResponse>
}