package id.bukusaku.bukusaku.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [CategoriesEntity::class, NewArticleEntity::class, ArticlesEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun newArticlesDao(): NewArticlesDao
    abstract fun articlesDao(): ArticlesDao
}