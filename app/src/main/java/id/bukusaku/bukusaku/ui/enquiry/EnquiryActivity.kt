package id.bukusaku.bukusaku.ui.enquiry

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.Enquiry
import id.bukusaku.bukusaku.utils.*
import kotlinx.android.synthetic.main.activity_enquiry.*
import org.jetbrains.anko.find
import org.koin.android.ext.android.inject

class EnquiryActivity : AppCompatActivity(), EnquiryView.View {
    private lateinit var alertDetail: AlertDialog.Builder
    private lateinit var alertLoading: SweetAlertDialog
    private lateinit var alertError: SweetAlertDialog
    private lateinit var alertInfo: SweetAlertDialog
    private lateinit var alertConfirm: SweetAlertDialog

    private val presenter: EnquiryPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enquiry)

        initView()
        initAlert()
    }

    private fun initView() {
        setSupportActionBar(toolbar_enquiry)
        supportActionBar?.title = getString(R.string.enquiry_title_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_submit.setOnClickListener { formValidation() }
    }

    private fun formValidation() {
        val id = intent.getIntExtra(PRODUCT_ID, 0)
        val name = edt_name.text.toString()
        val phone = edt_phone.text.toString()
        val jobs = edt_job.text.toString()
        val title = edt_title.text.toString()
        val message = edt_message.text.toString()
        val email = edt_email.text.toString()

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        when {
            name.isEmpty() || name.length < 3 -> {
                edt_name.error = getString(R.string.form_validation_empty_name)
            }
            phone.isEmpty() -> {
                edt_phone.error = getString(R.string.form_validation_empty_telp)
            }
            phone.length > 12 || phone.length < 7 -> {
                edt_phone.error = getString(R.string.form_validation_nonvalid_telp)
            }
            email.isEmpty() -> {
                edt_email.error = getString(R.string.form_validation_empty_email)
            }
            !email.matches(emailPattern) -> {
                edt_email.error = getString(R.string.form_validation_nonvalid_email)
            }
            jobs.isEmpty() -> {
                edt_job.error = getString(R.string.form_validation_empty_jobs)
            }
            title.isEmpty() -> {
                edt_title.error = getString(R.string.form_validation_empty_title)
            }
            message.isEmpty() -> {
                edt_message.error = getString(R.string.form_validation_empty_content)
            }
            message.length < 100 -> {
                edt_message.error = getString(R.string.form_validation_sort_content)
            }
            else -> {
                val confirmTitle = getString(R.string.confirm_enquiry_title)
                val content = getString(R.string.confirm_enquiry_content)
                val confirmYes = getString(R.string.confirm_enquiry_yes)
                val confirmCancel = getString(R.string.confirm_enquiry_cancel)
                alertConfirm.confirm(confirmTitle,content,confirmYes,confirmCancel){
                    presenter.sendEnquiry(Enquiry(id, name, phone, jobs, confirmTitle, message))
                    onAttachView()
                    showLoading()
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun showEnquiry(enquiry: Enquiry) {
        alertConfirm.dismiss()
        val title = getString(R.string.info_enquiry_title)
        val confirm = getString(R.string.info_enquiry_confirm)
        val cancel = getString(R.string.articles_error_alert_close)
        alertInfo.enquiry(title,confirm,cancel){
            val view = layoutInflater.inflate(R.layout.enquiry_response_layout,null)

            val tvName : TextView = view.find(R.id.tv_name)
            val tvPhone : TextView = view.find(R.id.tv_phone)
            val tvJobs : TextView = view.find(R.id.tv_jobs)
            val tvTitle: TextView = view.find(R.id.tv_title_enquiry)
            val tvEnquiry: TextView = view.find(R.id.tv_enquiry)

            tvName.text = enquiry.name
            tvPhone.text = enquiry.phone
            tvJobs.text = enquiry.job
            tvTitle.text = enquiry.title
            tvEnquiry.text = enquiry.message

            alertDetail.setTitle(getString(R.string.info_detail_enquiry_title))
            alertDetail.setIcon(R.drawable.ic_info)
            alertDetail.setView(view)
            alertDetail.setNegativeButton(getString(R.string.articles_error_alert_close)) {
                    dialog, _ -> dialog.dismiss()
            }
            alertDetail.show()
        }
        alertInfo.setCancelClickListener { this@EnquiryActivity.finish() }
    }

    override fun showLoading() {
        alertConfirm.dismiss()
        alertLoading.loading(getString(R.string.articles_alert_loading_title))
    }

    override fun hideView() {
        alertLoading.dismiss()
        container_enquiry.gone()
    }

    override fun onError(error: Throwable) {
        alertLoading.dismiss()
        val title = getString(R.string.articles_error_alert_title)
        val confirm = getString(R.string.articles_error_alert_close)
        alertError.successOrFailed(error.localizedMessage,title,confirm)
        alertError.setConfirmClickListener {
            container_enquiry.visible()
            alertError.dismiss()
        }
    }

    override fun onAttachView() { presenter.onAttach(this) }

    override fun onDetachView() { presenter.onDetach() }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }

    private fun initAlert() {
        alertLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        alertError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        alertInfo = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        alertConfirm = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        alertDetail = AlertDialog.Builder(this)
    }
}
