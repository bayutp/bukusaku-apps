package id.bukusaku.bukusaku.ui.enquiry

import id.bukusaku.bukusaku.base.BaseContract
import id.bukusaku.bukusaku.data.remote.Enquiry

interface EnquiryView {
    interface Presenter : BaseContract.Presenter<View> {
        fun sendEnquiry(enquiry: Enquiry)
    }

    interface View : BaseContract.View {
        fun showEnquiry(enquiry: Enquiry)
        fun onError(error: Throwable)
        fun showLoading()
        fun hideView()
    }

}