package id.bukusaku.bukusaku.ui.detail.product

import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProductDetailPresenter(
    private val apiRepo: AppRepo,
    private val compositeDisposable: CompositeDisposable
):ProductDetailContract.Presenter{

    private var mView:ProductDetailContract.View? = null
    override fun getProductById(id: Int) {
        apiRepo.getProductById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    mView?.showProduct(it.data)
                    mView?.showView()
                    Timber.d("data product detail : $it")
                },
                onError = {
                    mView?.onError(it)
                    Timber.e(it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun onAttach(view: ProductDetailContract.View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }

}