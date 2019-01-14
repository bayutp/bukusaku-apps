package id.bukusaku.bukusaku.data.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import id.bukusaku.bukusaku.data.local.dao.*
import id.bukusaku.bukusaku.data.local.entity.*
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.ProductDetail

@Database(
    entities = [CategoriesEntity::class, NewArticleEntity::class, ArticlesEntity::class, ProductEntity::class,
        ProductDetail::class, ArticleDetail::class], version = 1
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun newArticlesDao(): NewArticlesDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun productsDao(): ProductsDao
    abstract fun bookmarkProductDao(): BookmarkProductDao
    abstract fun bookmarkArticleDao(): BookmarkArticleDao
}