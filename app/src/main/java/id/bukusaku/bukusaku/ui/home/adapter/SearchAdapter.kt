package id.bukusaku.bukusaku.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_products.*

class SearchAdapter(private val products: MutableList<ProductDetail>, private val listener: (ProductDetail) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products, parent, false)
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(product: ProductDetail, listener: (ProductDetail) -> Unit) {
            img_logo_products.loadImage(product.imgUrl)
            tv_products.text = product.name
            tv_company.text = product.company
            tv_category.text = product.category.name
            itemView.setOnClickListener { listener(product) }
        }
    }
}