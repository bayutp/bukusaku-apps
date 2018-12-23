package id.bukusaku.bukusaku.ui.home

import com.google.gson.Gson
import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter(
    private val compositeDisposable: CompositeDisposable,
    private val appRepo: AppRepo
) : MainContract.Presenter {
    private var mView: MainContract.View? = null

    override fun getNewArticles() {
        appRepo.getNewArticles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { articles ->
                    mView?.showDataArticles(articles)
                    Timber.d("data articles: ${Gson().toJsonTree(articles)}")
                },
                onError = { error ->
                    mView?.onError(error)
                }
            ).addTo(compositeDisposable)
    }

    override fun getCategories() {
        appRepo.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { categories ->
                    mView?.showDataCategories(categories)
                    Timber.d("data categories: ${Gson().toJsonTree(categories)}")
                },
                onError = { error ->
                    mView?.onError(error)
                }
            ).addTo(compositeDisposable)
    }

    override fun onAttach(view: MainContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }

}