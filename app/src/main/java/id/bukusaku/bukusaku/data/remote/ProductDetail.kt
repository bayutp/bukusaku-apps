package id.bukusaku.bukusaku.data.remote

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("id")
    val id:Int,
    @SerializedName("product_name")
    val name:String?,
    @SerializedName("category")
    val category: CategoriesModel,
    @SerializedName("company")
    val company:String?,
    @SerializedName("description")
    val desc:String?,
    @SerializedName("logo_img")
    val imgUrl:String?,
    @SerializedName("web_link")
    val link:String?,
    @SerializedName("images")
    val images:List<ImageProduct>
)