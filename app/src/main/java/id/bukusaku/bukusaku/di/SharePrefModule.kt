package id.bukusaku.bukusaku.di

import id.bukusaku.bukusaku.utils.SharePreference
import org.koin.dsl.module.module

val spModule = module {
    single { SharePreference() }
}