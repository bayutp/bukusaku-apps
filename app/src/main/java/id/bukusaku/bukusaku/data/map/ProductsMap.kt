package id.bukusaku.bukusaku.data.map

import id.bukusaku.bukusaku.data.local.entity.ProductEntity
import id.bukusaku.bukusaku.data.remote.Products

data class ProductsMap(
    val id: Int,
    val name: String?,
    val company: String?,
    val imgUrl: String?,
    val category: String?
) {
    companion object {
        fun from(data: ProductEntity) = ProductsMap(data.id, data.name, data.company, data.imgUrl, data.category)

        fun from(data: Products) = ProductsMap(data.id, data.name, data.company, data.imgUrl, data.category)
    }
}