package id.bukusaku.bukusaku.data.response

import com.google.gson.annotations.SerializedName
import id.bukusaku.bukusaku.data.remote.Enquiry

data class EnquiryResponse(
    @SerializedName("data")
    val data: Enquiry
)