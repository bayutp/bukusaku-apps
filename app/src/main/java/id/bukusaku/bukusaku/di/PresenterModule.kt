package id.bukusaku.bukusaku.di

import id.bukusaku.bukusaku.ui.articles.ArticlePresenter
import id.bukusaku.bukusaku.ui.detail.article.ArticleDetailPresenter
import id.bukusaku.bukusaku.ui.detail.category.ProductsPresenter
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailPresenter
import id.bukusaku.bukusaku.ui.home.MainPresenter
import org.koin.dsl.module.module

val presenterModule = module {
    factory { MainPresenter(get(), get()) }
    factory { ArticlePresenter(get(), get()) }
    factory { ArticleDetailPresenter(get(), get()) }
    factory { ProductsPresenter(get(), get()) }
    factory { ProductDetailPresenter(get(), get()) }
}