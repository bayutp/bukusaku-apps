package id.bukusaku.bukusaku.ui.bookmark.articles

import id.bukusaku.bukusaku.data.repository.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarkArticlePresenter(private val repo: AppRepo) : BookmarkArticleContract.Presenter {
    private var mView: BookmarkArticleContract.View? = null

    override fun getAllArticles() {
        GlobalScope.launch(Dispatchers.Main) {
            val data = repo.getAllBookmarkArticle()
            if (data.isNullOrEmpty()) mView?.showEmpty() else mView?.onLoadArticles(data)
        }
    }

    override fun getSearchResult(query: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = repo.getArticlesBookmarkSearchResult("%$query%")
            if (data.isNullOrEmpty()) mView?.showNotFound() else mView?.showSearchResult(data)
        }
    }

    override fun onAttach(view: BookmarkArticleContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}