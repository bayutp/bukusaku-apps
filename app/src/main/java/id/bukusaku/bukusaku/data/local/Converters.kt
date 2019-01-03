package id.bukusaku.bukusaku.data.local

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.bukusaku.bukusaku.data.remote.CategoriesModel

class Converters {
    @TypeConverter
    fun fromCategory(category: CategoriesModel): String {
        val json = Gson().toJson(category)
        return json
    }

    @TypeConverter
    fun fromString(value: String): CategoriesModel {
        val type = object : TypeToken<CategoriesModel>() {}.type
        return Gson().fromJson<CategoriesModel>(value, type)
    }
}