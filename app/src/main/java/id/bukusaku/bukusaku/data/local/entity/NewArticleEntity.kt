package id.bukusaku.bukusaku.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.ArticlesModel
import id.bukusaku.bukusaku.data.remote.CategoriesModel

@Entity(tableName = "new_articles")
data class NewArticleEntity(@PrimaryKey(autoGenerate = false) val id: Int,
    val title: String?, @TypeConverters(Converters::class) val category: CategoriesModel,
    val imageUrl: String?) {
    companion object {
        fun from(data: ArticlesModel) = NewArticleEntity(data.id, data.title, data.category, data.imageUrl)
    }
}


@Entity(tableName = "articles")
data class ArticlesEntity(@PrimaryKey(autoGenerate = false) val id: Int, val title: String?,
    @TypeConverters(Converters::class) val category: CategoriesModel, val imageUrl: String?, val content: String?) {
    companion object {
        fun from(data: ArticleDetail) =
            ArticlesEntity(data.id, data.title, data.categories, data.imageUrl, data.content)
    }
}