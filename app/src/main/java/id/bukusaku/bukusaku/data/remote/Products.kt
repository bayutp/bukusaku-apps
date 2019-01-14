package id.bukusaku.bukusaku.data.remote

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_name")
    val name: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("logo_img")
    val imgUrl: String?,
    @SerializedName("category_name")
    val category: String?
)