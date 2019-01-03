package id.bukusaku.bukusaku.ui.detail.category

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.data.remote.Products

interface ProductsContract{
    interface Presenter:BaseContract.Presenter<View>{
        fun getProducts(categoryName:String)
    }

    interface View:BaseContract.View{
        fun showProducts(data:List<ProductsMap>)
        fun onError(error:Throwable)
        fun showView()
    }
}

