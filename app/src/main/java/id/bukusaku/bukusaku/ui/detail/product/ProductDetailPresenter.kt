package id.bukusaku.bukusaku.ui.detail.product

import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDetailPresenter(private val apiRepo: AppRepo, private val compositeDisposable: CompositeDisposable)
    : ProductDetailContract.Presenter {

    private var mView: ProductDetailContract.View? = null

    override fun getProductById(id: Int) {
        mView?.hideView()
        apiRepo.getProductById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    mView?.showProduct(it.data)
                },
                onError = {
                    mView?.onError(it)
                }
            ).addTo(compositeDisposable)
    }

    override fun bookmark(data: ProductDetail) {
        GlobalScope.launch(Dispatchers.Main) { apiRepo.bookmarkProduct(data) }
    }

    override fun bookmarkStatus(id: Int) {
        GlobalScope.launch(Dispatchers.Main) { mView?.showStatus(apiRepo.getBookmarkProduct(id)) }
    }

    override fun deleteFromBookmark(id: Int) { GlobalScope.launch(Dispatchers.Main) { apiRepo.deleteBookmark(id) } }

    override fun onAttach(view: ProductDetailContract.View) { mView = view }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }
}