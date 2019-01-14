package id.bukusaku.bukusaku.ui.bookmark.articles

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.remote.ArticleDetail

interface BookmarkArticleContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getAllArticles()
        fun getSearchResult(query: String?)
    }

    interface View : BaseContract.View {
        fun onLoadArticles(data: List<ArticleDetail>?)
        fun showSearchResult(data: List<ArticleDetail>)
        fun showNotFound()
        fun showEmpty()
    }
}