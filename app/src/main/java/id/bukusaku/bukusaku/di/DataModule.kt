package id.bukusaku.bukusaku.di

import id.bukusaku.bukusaku.data.repository.AppRepo
import id.bukusaku.bukusaku.data.repository.LocalDataSource
import id.bukusaku.bukusaku.data.repository.RemoteDataSource
import org.koin.dsl.module.module

val dataModule = module {
    single { AppRepo(get(), get()) }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get(), get(), get(), get(), get(), get()) }
}