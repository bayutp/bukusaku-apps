package id.bukusaku.bukusaku.ui.bookmark.products

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.remote.ProductDetail

interface BookmarkProductContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getProductBookmark()
        fun getSearchResult(query: String?)
    }

    interface View : BaseContract.View {
        fun onLoadData(data: List<ProductDetail>?)
        fun showSearchResult(data: List<ProductDetail>)
        fun showNotFound()
        fun showEmpty()
    }
}