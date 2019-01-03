package id.bukusaku.bukusaku.data.remote

import com.google.gson.annotations.SerializedName

data class ImageProduct(
    @SerializedName("id")
    val id:Int,
    @SerializedName("product_id")
    val productId:Int,
    @SerializedName("link")
    val imgUrl:String?
)