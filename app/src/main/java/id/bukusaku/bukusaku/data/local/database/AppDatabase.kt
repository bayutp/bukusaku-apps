package id.bukusaku.bukusaku.data.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import id.bukusaku.bukusaku.data.local.entity.Converters
import id.bukusaku.bukusaku.data.local.dao.ArticlesDao
import id.bukusaku.bukusaku.data.local.dao.CategoriesDao
import id.bukusaku.bukusaku.data.local.dao.NewArticlesDao
import id.bukusaku.bukusaku.data.local.dao.ProductsDao
import id.bukusaku.bukusaku.data.local.entity.ArticlesEntity
import id.bukusaku.bukusaku.data.local.entity.CategoriesEntity
import id.bukusaku.bukusaku.data.local.entity.NewArticleEntity
import id.bukusaku.bukusaku.data.local.entity.ProductEntity

@Database(entities = [CategoriesEntity::class, NewArticleEntity::class,
        ArticlesEntity::class, ProductEntity::class], version = 1)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun newArticlesDao(): NewArticlesDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun productsDao(): ProductsDao
}