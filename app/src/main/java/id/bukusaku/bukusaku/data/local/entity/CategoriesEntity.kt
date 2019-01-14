package id.bukusaku.bukusaku.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import id.bukusaku.bukusaku.data.remote.CategoriesModel

@Entity(tableName = "categories")
data class CategoriesEntity(@PrimaryKey(autoGenerate = false) val id: Int,
    val name: String?, val color: String?, val icon: String?) {
    companion object {
        fun from(data: CategoriesModel) = CategoriesEntity(data.id, data.name, data.color, data.icon)
    }
}