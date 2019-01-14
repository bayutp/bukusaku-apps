package id.bukusaku.bukusaku.ui.bookmark.products

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_products.*

class BookmarkProductAdapter(
    private val dataBookmark: MutableList<ProductDetail>,
    private val listener: (ProductDetail) -> Unit
) : RecyclerView.Adapter<BookmarkProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products, parent, false)
    )

    override fun getItemCount() = dataBookmark.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataBookmark[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: ProductDetail, listener: (ProductDetail) -> Unit) {
            img_logo_products.loadImage(data.imgUrl)
            tv_products.text = data.name
            tv_company.text = data.company
            tv_category.text = data.category.name
            itemView.setOnClickListener { listener(data) }
        }
    }
}