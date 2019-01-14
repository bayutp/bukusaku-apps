package id.bukusaku.bukusaku.ui.articles

import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticlePresenter(private val appRepo: AppRepo, private val compositeDisposable: CompositeDisposable) :
    ArticlesContract.Presenter {
    private var mView: ArticlesContract.View? = null

    override fun getArticles() {
        appRepo.getArticles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { mView?.showArticles(it) },
                onError = { mView?.onError(it) }
            ).addTo(compositeDisposable)
    }

    override fun getSearchResult(query: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = appRepo.getArticlesSearchResult("%$query%")
            if (data.isEmpty()) mView?.showEmpty() else mView?.showSearchResult(data)
        }
    }

    override fun onAttach(view: ArticlesContract.View) { mView = view }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }
}