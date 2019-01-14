package id.bukusaku.bukusaku.data.remote

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ArticlesModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("category")
    val category: CategoriesModel,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("reff_link")
    val link: String?
)

@Entity(tableName = "article_bookmark")
data class ArticleDetail(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("category")
    val categories: CategoriesModel,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("reff_link")
    val link: String?
)