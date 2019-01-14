package id.bukusaku.bukusaku.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_name")
    val name: String?,
    @SerializedName("hex_color")
    val color: String?,
    @SerializedName("icon")
    val icon: String?
) : Parcelable