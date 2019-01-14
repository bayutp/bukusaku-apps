package id.bukusaku.bukusaku.ui.articles

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.map.Articles

interface ArticlesContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getArticles()
        fun getSearchResult(query: String?)
    }

    interface View : BaseContract.View {
        fun showArticles(data: List<Articles>)
        fun showSearchResult(data: List<Articles>)
        fun showEmpty()
        fun onError(error: Throwable)
    }
}