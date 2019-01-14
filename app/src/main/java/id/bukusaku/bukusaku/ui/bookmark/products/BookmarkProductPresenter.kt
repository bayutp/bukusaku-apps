package id.bukusaku.bukusaku.ui.bookmark.products

import id.bukusaku.bukusaku.data.repository.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarkProductPresenter(private val repo: AppRepo) : BookmarkProductContract.Presenter {
    private var mView: BookmarkProductContract.View? = null

    override fun getProductBookmark() {
        GlobalScope.launch(Dispatchers.Main) {
            val data = repo.getAllBookmarkProduct()
            if (data.isNullOrEmpty()) mView?.showEmpty() else mView?.onLoadData(data)
        }
    }

    override fun getSearchResult(query: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = repo.getProductsBookmarkSearchResult("%$query%")
            if (data.isNullOrEmpty()) mView?.showNotFound() else mView?.showSearchResult(data)
        }
    }

    override fun onAttach(view: BookmarkProductContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}