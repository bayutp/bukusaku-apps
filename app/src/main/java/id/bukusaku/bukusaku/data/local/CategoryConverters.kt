package id.bukusaku.bukusaku.data.local

import android.arch.persistence.room.TypeConverter
import id.bukusaku.bukusaku.data.remote.CategoriesModel

class CategoryConverters{
    companion object {
        @TypeConverter
        @JvmStatic
        fun getId(categoriesModel: CategoriesModel):Int{
            return categoriesModel.id
        }
    }
}