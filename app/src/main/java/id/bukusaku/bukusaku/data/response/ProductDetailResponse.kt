package id.bukusaku.bukusaku.data.response

import com.google.gson.annotations.SerializedName
import id.bukusaku.bukusaku.data.remote.ProductDetail

data class ProductDetailResponse(
    @SerializedName("status")
    val status:String?,
    @SerializedName("data")
    val data: ProductDetail
)