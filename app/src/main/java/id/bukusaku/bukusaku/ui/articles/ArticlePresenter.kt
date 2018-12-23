package id.bukusaku.bukusaku.ui.articles

import com.google.gson.Gson
import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android
import org.jetbrains.anko.ScreenSize
import timber.log.Timber

class ArticlePresenter(
    private val appRepo: AppRepo,
    private val compositeDisposable: CompositeDisposable
) : ArticlesContract.Presenter {
    private var mView: ArticlesContract.View? = null
    override fun getArticles() {
        appRepo.getArticles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { articles ->
                    mView?.showArticles(articles)
                    Timber.d("cek articles : ${Gson().toJsonTree(articles)}")
                },
                onError = { error ->
                    mView?.onError(error)
                }
            ).addTo(compositeDisposable)
    }

    override fun onAttach(view: ArticlesContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }

}