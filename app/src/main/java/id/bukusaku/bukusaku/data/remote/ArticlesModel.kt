package id.bukusaku.bukusaku.data.remote

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

data class ArticleDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("category")
    val categories: CategoriesModel,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("content")
    val content:String?,
    @SerializedName("reff_link")
    val link: String?
)