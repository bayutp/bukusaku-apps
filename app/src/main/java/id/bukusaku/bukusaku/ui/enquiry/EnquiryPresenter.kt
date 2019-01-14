package id.bukusaku.bukusaku.ui.enquiry

import id.bukusaku.bukusaku.data.remote.Enquiry
import id.bukusaku.bukusaku.data.repository.AppRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class EnquiryPresenter(private val appRepo: AppRepo, private val compositeDisposable: CompositeDisposable)
    : EnquiryView.Presenter {
    private var mView: EnquiryView.View? = null

    override fun sendEnquiry(enquiry: Enquiry) {
        mView?.showLoading()
        appRepo.sendEnquiry(enquiry)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    mView?.hideView()
                    mView?.showEnquiry(it.data)
                },
                onError = {
                    mView?.hideView()
                    mView?.onError(it)
                }
            ).addTo(compositeDisposable)
    }

    override fun onAttach(view: EnquiryView.View) { mView = view }

    override fun onDetach() {
        mView = null
        compositeDisposable.clear()
    }
}