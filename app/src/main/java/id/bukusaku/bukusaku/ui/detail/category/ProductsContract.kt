package id.bukusaku.bukusaku.ui.detail.category

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.map.ProductsMap

interface ProductsContract {
    interface Presenter : BaseContract.Presenter<View> {
        fun getProducts(categoryName: String)
        fun getProductsSearchResult(query: String?, categoryName: String?)
    }

    interface View : BaseContract.View {
        fun showProducts(data: List<ProductsMap>)
        fun showSearchResult(data: List<ProductsMap>)
        fun showEmpty()
        fun onError(error: Throwable)
        fun showView()
    }
}

