package id.bukusaku.bukusaku.ui.articles

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.data.remote.ArticleDetail

interface ArticlesContract{
    interface Presenter:BaseContract.Presenter<View>{
        fun getArticles()
    }
    interface View:BaseContract.View{
        fun showArticles(data:List<Articles>)
        fun onError(error:Throwable)
    }
}