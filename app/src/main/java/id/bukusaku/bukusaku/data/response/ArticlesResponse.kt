package id.bukusaku.bukusaku.data.response

import com.google.gson.annotations.SerializedName
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.ArticlesModel

data class ArticlesResponse(
    @SerializedName("status")
    val status:String,
    @SerializedName("data")
    val data : List<ArticlesModel>
)

data class ArticleDetailResponse(
    @SerializedName("status")
    val status:String,
    @SerializedName("data")
    val data : List<ArticleDetail>
)