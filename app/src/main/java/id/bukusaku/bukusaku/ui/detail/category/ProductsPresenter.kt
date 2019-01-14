package id.bukusaku.bukusaku.ui.detail.category

import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductsPresenter(private val compositeDisposable: CompositeDisposable, private val appRepo: AppRepo)
    : ProductsContract.Presenter {
    private var mView: ProductsContract.View? = null

    override fun getProducts(categoryName: String) {
        appRepo.getProducts(categoryName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    mView?.showProducts(it)
                    mView?.showView()
                },
                onError = { mView?.onError(it) }
            ).addTo(compositeDisposable)
    }

    override fun getProductsSearchResult(query: String?, categoryName: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = appRepo.getProductsSearchResult("%$query%", categoryName)
            if (data.isEmpty()) mView?.showEmpty() else mView?.showSearchResult(data)
        }
    }

    override fun onAttach(view: ProductsContract.View) { mView = view }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }

}