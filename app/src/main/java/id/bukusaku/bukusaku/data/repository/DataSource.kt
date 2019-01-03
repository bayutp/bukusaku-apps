package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import io.reactivex.Single

interface DataSource{
    fun getCategories():Single<List<Categories>>
    fun getNewArticles():Single<List<NewArticles>>
    fun getArticles():Single<List<Articles>>
}