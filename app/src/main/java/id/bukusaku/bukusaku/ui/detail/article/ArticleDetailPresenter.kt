package id.bukusaku.bukusaku.ui.detail.article

import com.google.gson.Gson
import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ArticleDetailPresenter(
    private val appRepo: AppRepo,
    private val compositeDisposable: CompositeDisposable
) : ArticleDetailContract.Presenter {

    private var mView: ArticleDetailContract.View? = null

    override fun getArticleById(id: Int) {
        appRepo.getArticleById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    articleDetail -> mView?.showArticle(articleDetail.data)
                    mView?.showView()
                    Timber.d("data articleDetail : ${Gson().toJsonTree(articleDetail)}}")
                },
                onError = {
                    mView?.onError(it)
                    Timber.d("error article detail ${it.localizedMessage}")
                }
            ).addTo(compositeDisposable)
    }

    override fun onAttach(view: ArticleDetailContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }

}