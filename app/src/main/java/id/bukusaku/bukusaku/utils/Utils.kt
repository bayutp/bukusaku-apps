package id.bukusaku.bukusaku.utils

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bukusaku.bukusaku.R

const val ARTICLE_ID = "ARTICLE_ID"

const val PRODUCT_ID = "PRODUCT_ID"

const val PRODUCT_NAME = "PRODUCT_NAME"

fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadImageArticle(url: String?) {
    val option = RequestOptions()
        .centerCrop()
    Glide.with(context)
        .asBitmap()
        .apply(option)
        .load(url)
        .into(this)
}

fun SweetAlertDialog.error(title:String, confirmText:String?, errorMsg:String?, cancel:String?){
    this.titleText = title
    this.contentText = errorMsg
    this.setCancelable(false)
    this.confirmText = confirmText
    this.cancelText = cancel
    this.show()
}

fun SweetAlertDialog.loading(title:String?){
    this.progressHelper.barColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
    this.titleText = title
    this.setCancelable(false)
    this.show()
}

fun SweetAlertDialog.successOrFailed(message:String, title:String, confirmText: String? ){
    this.titleText = title
    this.contentText = message
    this.setCancelable(false)
    this.confirmText = confirmText
    this.setConfirmClickListener { this.dismiss() }
    this.show()
}

fun SweetAlertDialog.confirm(title:String, message:String,
                             confirmText: String?, cancel:String?, listener: (SweetAlertDialog) -> Unit){
    this.titleText = title
    this.contentText = message
    this.setCancelable(false)
    this.confirmText = confirmText
    this.setConfirmClickListener(listener)
    this.cancelText = cancel
    this.setCancelClickListener{this.dismiss()}
    this.show()
}

fun SweetAlertDialog.enquiry(title:String, confirmText: String?,
                             cancelText:String, listener:(SweetAlertDialog) -> Unit){
    this.titleText = title
    this.confirmText = confirmText
    this.setCancelable(false)
    this.setConfirmClickListener(listener)
    this.cancelText = cancelText
    this.show()
}

fun toUpperFirstWord(searchQuery: String?): String {
    val query = searchQuery?.replace("_", " ")
    val queries = query?.split(" ")?.toMutableList()
    var queryOutput = ""
    if (queries != null) {
        for (word in queries) queryOutput += word.capitalize() + " "
    }
    queryOutput = queryOutput.trim()
    return queryOutput
}

fun View.visible() { visibility = View.VISIBLE }

fun View.gone() { visibility = View.GONE }

fun View.invisible() { visibility = View.INVISIBLE }