package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.data.response.ArticleDetailResponse
import id.bukusaku.bukusaku.data.response.ArticleDetailResponseById
import id.bukusaku.bukusaku.data.response.ProductDetailResponse
import id.bukusaku.bukusaku.data.response.ProductsResponse
import io.reactivex.Single

interface DataSource {
    fun getCategories(): Single<List<Categories>>

    fun getNewArticles(): Single<List<NewArticles>>

    fun getArticles(): Single<List<Articles>>

    fun getArticleById(id: Int): Single<ArticleDetailResponseById>

    fun getProducts(categoryName: String): Single<List<ProductsMap>>

    fun getProductById(id:Int): Single<ProductDetailResponse>
}