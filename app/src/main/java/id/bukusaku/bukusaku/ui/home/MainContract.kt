package id.bukusaku.bukusaku.ui.home

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles

interface MainContract{
    interface Presenter:BaseContract.Presenter<View>{
        fun getCategories()
        fun getNewArticles()
    }

    interface View : BaseContract.View{
        fun showDataCategories(data:List<Categories>)
        fun showDataArticles(data:List<NewArticles>)
        fun onError(error:Throwable)
    }
}