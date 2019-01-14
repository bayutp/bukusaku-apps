package id.bukusaku.bukusaku.data.remote

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_bookmark")
data class ProductDetail(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("product_name")
    val name: String?,
    @SerializedName("category")
    val category: CategoriesModel,
    @SerializedName("company")
    val company: String?,
    @SerializedName("description")
    val desc: String?,
    @SerializedName("logo_img")
    val imgUrl: String?,
    @SerializedName("web_link")
    val link: String?,
    @SerializedName("images")
    val images: List<ImageProduct>
)