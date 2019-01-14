package id.bukusaku.bukusaku.data.local.entity

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.bukusaku.bukusaku.data.remote.CategoriesModel
import id.bukusaku.bukusaku.data.remote.ImageProduct

open class Converters {
    @TypeConverter
    fun fromCategory(category: CategoriesModel): String = Gson().toJson(category)

    @TypeConverter
    fun fromImage(image: List<ImageProduct>): String = Gson().toJson(image)

    @TypeConverter
    fun fromString(value: String): CategoriesModel {
        val type = object : TypeToken<CategoriesModel>() {}.type
        return Gson().fromJson<CategoriesModel>(value, type)
    }

    @TypeConverter
    fun fromStringToImage(value: String): List<ImageProduct> {
        val type = object : TypeToken<List<ImageProduct>>() {}.type
        return Gson().fromJson<List<ImageProduct>>(value, type)
    }
}