package id.bukusaku.bukusaku.di

import android.arch.persistence.room.Room
import id.bukusaku.bukusaku.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dbModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "bukusaku")
            .fallbackToDestructiveMigration().build()
    }
    single { get<AppDatabase>().categoriesDao() }
    single { get<AppDatabase>().newArticlesDao() }
    single { get<AppDatabase>().articlesDao() }
    single { get<AppDatabase>().productsDao() }
    single { get<AppDatabase>().bookmarkProductDao() }
    single { get<AppDatabase>().bookmarkArticleDao() }
}