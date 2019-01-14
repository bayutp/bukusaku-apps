package id.bukusaku.bukusaku.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import id.bukusaku.bukusaku.data.remote.Products

@Entity(tableName = "products")
data class ProductEntity(@PrimaryKey(autoGenerate = false) val id: Int, val name: String?,
    val company: String?, val imgUrl: String?, val category: String?) {
    companion object {
        fun from(data: Products) = ProductEntity(data.id, data.name, data.company, data.imgUrl, data.category)
    }
}