package id.bukusaku.bukusaku.di

import id.bukusaku.bukusaku.ui.articles.ArticlePresenter
import id.bukusaku.bukusaku.ui.home.MainPresenter
import org.koin.dsl.module.module

val presenterModule = module {
    factory { MainPresenter(get(), get()) }
    factory { ArticlePresenter(get(),get()) }
}