package id.bukusaku.bukusaku.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

const val ARTICLE_ID = "ARTICLE_ID"

const val PRODUCT_ID = "PRODUCT_ID"

const val PRODUCT_NAME = "PRODUCT_NAME"

const val CATEGORY_ID = "CATEGORY_ID"

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

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}