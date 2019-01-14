package id.bukusaku.bukusaku.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Enquiry(
    @SerializedName("product_id")
    val idProduct: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_number")
    val phone: String?,
    @SerializedName("job")
    val job: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("body")
    val message: String?
) : Parcelable