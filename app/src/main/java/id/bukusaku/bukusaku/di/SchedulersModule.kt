package id.bukusaku.bukusaku.di

import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module.module

val schedulersModule = module {
    factory { CompositeDisposable() }
}