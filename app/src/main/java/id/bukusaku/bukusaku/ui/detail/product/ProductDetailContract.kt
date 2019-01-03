package id.bukusaku.bukusaku.ui.detail.product

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.remote.ProductDetail

interface ProductDetailContract{
    interface Presenter:BaseContract.Presenter<View>{
        fun getProductById(id:Int)
    }
    interface View:BaseContract.View{
        fun showProduct(data:ProductDetail)
        fun onError(error:Throwable)
        fun showView()
    }
}