package id.bukusaku.bukusaku.ui.detail.article

import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleDetailPresenter(
    private val appRepo: AppRepo,
    private val compositeDisposable: CompositeDisposable
) : ArticleDetailContract.Presenter {

    private var mView: ArticleDetailContract.View? = null

    override fun getArticleById(id: Int) {
        mView?.showLoading()
        appRepo.getArticleById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    articleDetail -> mView?.showArticle(articleDetail.data)
                    mView?.showView()
                },
                onError = {
                    mView?.onError(it)
                }
            ).addTo(compositeDisposable)
    }

    override fun addToBookmark(data: ArticleDetail) {
        GlobalScope.launch(Dispatchers.Main){ appRepo.bookmarkArticle(data) }
    }

    override fun statusBookmark(id: Int) {
        GlobalScope.launch(Dispatchers.Main){ mView?.showStatus(appRepo.getBookmarkArticleById(id)) }
    }

    override fun deleteFromBookmark(id: Int) {
        GlobalScope.launch(Dispatchers.Main){ appRepo.deleteBookmarkArticle(id) }
    }

    override fun onAttach(view: ArticleDetailContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }

}