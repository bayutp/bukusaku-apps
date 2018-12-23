package id.bukusaku.bukusaku.data.map

import id.bukusaku.bukusaku.data.local.CategoriesEntity
import id.bukusaku.bukusaku.data.remote.CategoriesModel

data class Categories(
    val id: Int,
    val name: String?,
    val color: String?,
    val icon: String?
) {
    companion object {
        fun from(data: CategoriesEntity) = Categories(
            data.id,
            data.name,
            data.color,
            data.icon
        )

        fun from(data: CategoriesModel) = Categories(
            data.id,
            data.name,
            data.color,
            data.icon
        )
    }
}