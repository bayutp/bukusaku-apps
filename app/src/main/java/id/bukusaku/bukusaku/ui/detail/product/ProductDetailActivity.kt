package id.bukusaku.bukusaku.ui.detail.product

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import cn.pedant.SweetAlert.SweetAlertDialog
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ImageProduct
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.ui.enquiry.EnquiryActivity
import id.bukusaku.bukusaku.utils.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private val imageList: MutableList<ImageProduct> = mutableListOf()
    private var data: ProductDetail? = null
    private var isBookmark: Boolean = false
    private var menu: Menu? = null
    private var idProduct: Int = 0
    private val presenter: ProductDetailPresenter by inject()

    private lateinit var alertLoading: SweetAlertDialog
    private lateinit var adapter: ProductDetailAdapter
    private lateinit var alertError: SweetAlertDialog
    private lateinit var alertBookmarkSuccess : SweetAlertDialog
    private lateinit var alertBookmarkFailed : SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        getProductDetail()
        initView()
        initAlert()
    }

    private fun initView() {
        adapter = ProductDetailAdapter(imageList)
        rv_product_images.layoutManager = LinearLayoutManager(this@ProductDetailActivity,
            LinearLayoutManager.HORIZONTAL, false)
        rv_product_images.adapter = adapter
    }

    private fun initAlert() {
        alertLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        alertError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        alertBookmarkSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        alertBookmarkFailed = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        showLoading()
    }

    private fun getProductDetail() {
        idProduct = intent.getIntExtra(PRODUCT_ID, 0)
        presenter.getProductById(idProduct)
        onAttachView()
    }

    override fun showProduct(data: ProductDetail) {
        alertLoading.dismiss()

        setSupportActionBar(toolbar_main_product)
        supportActionBar?.title = data.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.data = data
        idProduct = data.id

        presenter.bookmarkStatus(idProduct)

        img_product_detail.loadImage(data.imgUrl)
        tv_product_title.text = data.name
        tv_product_category.text = data.category.name
        tv_product_company.text = data.company
        tv_product_content.text = data.desc

        imageList.clear()
        imageList.addAll(data.images)
        adapter.notifyDataSetChanged()

        btn_inquiry.setOnClickListener { onInquiry() }
        btn_visit.setOnClickListener { onVisitProduct(data.link) }

        container_product_detail.visible()
    }

    override fun hideView() {
        alertError.dismiss()
        showLoading()
        container_product_detail.invisible()
    }

    private fun showSuccess(message:String, title:String){
        alertBookmarkFailed.dismiss()
        alertBookmarkSuccess.successOrFailed(message,title,getString(R.string.articles_success_alert_confirm))
    }

    private fun showFailed(message: String){
        alertBookmarkSuccess.dismiss()
        val title = getString(R.string.articles_error_alert_title)
        val confirmText = getString(R.string.articles_error_alert_confirm)
        alertBookmarkFailed.successOrFailed(message, title, confirmText)
    }

    private fun showLoading() {
        alertError.dismiss()
        alertLoading.loading(getString(R.string.articles_alert_loading_title))
    }

    private fun onVisitProduct(url: String?) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        builder.addDefaultShareMenuItem()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    private fun onInquiry() { startActivity<EnquiryActivity>(PRODUCT_ID to idProduct) }

    override fun onError(error: Throwable) {
        alertLoading.dismiss()
        val title = getString(R.string.articles_error_alert_title)
        val confirm = getString(R.string.articles_error_alert_confirm)
        val cancelText = getString(R.string.articles_error_alert_close)
        alertError.setConfirmClickListener { getProductDetail() }
        alertError.setCancelClickListener { this.finish() }
        alertError.error(title,confirm,error.localizedMessage,cancelText)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        this.menu = menu
        setBookmark()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.bookmark_product_menu -> {
                if(isBookmark) removeFromBookmark() else addToBookmark()
                isBookmark = !isBookmark
                setBookmark()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun setBookmark() {
        if (isBookmark) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border)
        }
    }

    private fun addToBookmark() {
        try {
            data?.let { presenter.bookmark(it) }
            val message = getString(R.string.bookmark_products_success_message)
            val title = getString(R.string.bookmark_articles_success_title)
            showSuccess(message,title)
        } catch (e: Throwable) {
            showFailed(getString(R.string.bookmark_products_error, e.localizedMessage))
        }
    }

    private fun removeFromBookmark(){
        try {
            presenter.deleteFromBookmark(idProduct)
            val message = getString(R.string.delete_bookmark_products_message)
            val title = getString(R.string.delete_bookmark_articles_title)
            showSuccess(message, title)
        }catch (e:Throwable){
            showFailed(getString(R.string.delete_bookmark_products_error,e.localizedMessage))
        }
    }


    override fun showStatus(data: ProductDetail?) {
        if (data != null) {
            isBookmark = true
            setBookmark()
        }
    }

    override fun onAttachView() { presenter.onAttach(this) }

    override fun onDetachView() { presenter.onDetach() }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}