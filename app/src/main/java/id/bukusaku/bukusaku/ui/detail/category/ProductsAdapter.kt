package id.bukusaku.bukusaku.ui.detail.category

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_products.*

class ProductsAdapter(private val products: MutableList<ProductsMap>, private val listener: (ProductsMap) -> Unit)
    : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_products, parent, false))

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(product: ProductsMap, listener: (ProductsMap) -> Unit) {
            img_logo_products.loadImage(product.imgUrl)
            tv_products.text = product.name
            tv_company.text = product.company
            tv_category.text = product.category
            itemView.setOnClickListener { listener(product) }
        }
    }
}