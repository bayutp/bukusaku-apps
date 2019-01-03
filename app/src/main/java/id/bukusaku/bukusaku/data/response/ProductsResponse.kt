package id.bukusaku.bukusaku.data.response

import com.google.gson.annotations.SerializedName
import id.bukusaku.bukusaku.data.remote.Products

data class ProductsResponse(
    @SerializedName("status")
    val status:String?,
    @SerializedName("data")
    val data:List<Products>
)