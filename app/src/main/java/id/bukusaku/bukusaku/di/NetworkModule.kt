package id.bukusaku.bukusaku.di

import id.bukusaku.bukusaku.network.ApiClient
import org.koin.dsl.module.module

val networkModule = module {
    single { ApiClient().create() }
}