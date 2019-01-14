package id.bukusaku.bukusaku.di

import id.bukusaku.bukusaku.ui.articles.ArticlePresenter
import id.bukusaku.bukusaku.ui.bookmark.articles.BookmarkArticlePresenter
import id.bukusaku.bukusaku.ui.bookmark.products.BookmarkProductPresenter
import id.bukusaku.bukusaku.ui.detail.article.ArticleDetailPresenter
import id.bukusaku.bukusaku.ui.detail.category.ProductsPresenter
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailPresenter
import id.bukusaku.bukusaku.ui.enquiry.EnquiryPresenter
import id.bukusaku.bukusaku.ui.home.HomePresenter
import org.koin.dsl.module.module

val presenterModule = module {
    factory { HomePresenter(get(), get()) }
    factory { ArticlePresenter(get(), get()) }
    factory { ArticleDetailPresenter(get(), get()) }
    factory { ProductsPresenter(get(), get()) }
    factory { ProductDetailPresenter(get(), get()) }
    factory { EnquiryPresenter(get(), get()) }
    factory { BookmarkProductPresenter(get()) }
    factory { BookmarkArticlePresenter(get()) }
}