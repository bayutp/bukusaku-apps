package id.bukusaku.bukusaku.ui.home

import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val compositeDisposable: CompositeDisposable, private val appRepo: AppRepo) :
    HomeContract.Presenter {
    private var mView: HomeContract.View? = null

    override fun getNewArticles() {
        appRepo.getNewArticles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { articles -> mView?.showDataArticles(articles) },
                onError = { error -> mView?.onError(error) }
            ).addTo(compositeDisposable)
    }

    override fun getCategories() {
        appRepo.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { categories -> mView?.showDataCategories(categories) },
                onError = { error -> mView?.onError(error) }
            ).addTo(compositeDisposable)
    }

    override fun getSearchResult(query: String?) {
        appRepo.getSearchProductResult(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { if (it.data.isNullOrEmpty()) mView?.showEmpty() else mView?.showSearchResult(it.data) },
                onError = { mView?.onErrorSearch(it) }
            ).addTo(compositeDisposable)
    }

    override fun onAttach(view: HomeContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }
}