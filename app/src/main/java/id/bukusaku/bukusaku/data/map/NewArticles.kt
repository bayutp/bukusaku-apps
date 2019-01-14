package id.bukusaku.bukusaku.data.map

import id.bukusaku.bukusaku.data.local.entity.ArticlesEntity
import id.bukusaku.bukusaku.data.local.entity.NewArticleEntity
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.ArticlesModel
import id.bukusaku.bukusaku.data.remote.CategoriesModel

data class NewArticles(val id: Int, val title: String?, val category: CategoriesModel, val imageUrl: String?) {
    companion object {
        fun from(data: NewArticleEntity) = NewArticles(data.id, data.title, data.category, data.imageUrl)
        fun from(data: ArticlesModel) = NewArticles(data.id, data.title, data.category, data.imageUrl)
    }
}

data class Articles(val id: Int, val title: String?, val category: CategoriesModel, val imageUrl: String?,
                    val content: String?) {
    companion object {
        fun from(data: ArticlesEntity) = Articles(data.id, data.title, data.category, data.imageUrl, data.content)
        fun from(data: ArticleDetail) = Articles(data.id, data.title, data.categories, data.imageUrl, data.content)
    }
}