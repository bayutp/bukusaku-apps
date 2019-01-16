package id.bukusaku.bukusaku.ui.home

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.data.remote.ProductDetail

interface HomeContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getCategories()
        fun getNewArticles()
        fun getSearchResult(query: String?)
    }

    interface View : BaseContract.View {
        fun showDataCategories(data: List<Categories>)
        fun showDataArticles(data: List<NewArticles>)
        fun showSearchResult(data: List<ProductDetail>)
        fun showEmpty()
        fun onError(error: Throwable)
        fun onErrorSearch(error: Throwable)
    }
}