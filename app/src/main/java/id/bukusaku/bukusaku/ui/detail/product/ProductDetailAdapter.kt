package id.bukusaku.bukusaku.ui.detail.product

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ImageProduct
import id.bukusaku.bukusaku.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product_image.*

class ProductDetailAdapter(private val imagesList: MutableList<ImageProduct>,
                           private val listener: (List<ImageProduct>, Int) -> Unit) : RecyclerView.Adapter<ProductDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_product_image, parent, false))

    override fun getItemCount() = imagesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imagesList[position], imagesList,listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(images: ImageProduct,
                 imagesList: MutableList<ImageProduct>,
                 listener: (List<ImageProduct>, Int) -> Unit) {

            img_product_list.loadImage(images.imgUrl)
            img_product_list.setOnClickListener {
                val position = adapterPosition
                listener(imagesList, position) }
        }
    }
}