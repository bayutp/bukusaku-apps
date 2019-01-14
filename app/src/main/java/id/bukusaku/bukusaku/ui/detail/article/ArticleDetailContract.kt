package id.bukusaku.bukusaku.ui.detail.article

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.remote.ArticleDetail

interface ArticleDetailContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getArticleById(id: Int)
        fun addToBookmark(data: ArticleDetail)
        fun deleteFromBookmark(id: Int)
        fun statusBookmark(id: Int)
    }

    interface View : BaseContract.View {
        fun showArticle(data: ArticleDetail)
        fun showView()
        fun showLoading()
        fun showStatus(data: ArticleDetail?)
        fun onError(error: Throwable)
    }
}