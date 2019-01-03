package id.bukusaku.bukusaku.ui.detail.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ImageProduct
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.utils.PRODUCT_ID
import id.bukusaku.bukusaku.utils.loadImage
import id.bukusaku.bukusaku.utils.visible
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private val presenter: ProductDetailPresenter by inject()
    private lateinit var adapter: ProductDetailAdapter
    private val imageList: MutableList<ImageProduct> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        getProductDetail()
        initView()
    }

    private fun initView() {
        adapter = ProductDetailAdapter(imageList)
        rv_product_images.layoutManager = LinearLayoutManager(this@ProductDetailActivity,
            LinearLayoutManager.HORIZONTAL,false)
        rv_product_images.adapter = adapter
        swipe_refresh_product.setOnRefreshListener { getProductDetail() }
    }

    private fun getProductDetail() {
        val id = intent.getIntExtra(PRODUCT_ID, 0)
        presenter.getProductById(id)
        onAttachView()
    }

    override fun showProduct(data: ProductDetail) {
        swipe_refresh_product.isRefreshing = false
        setSupportActionBar(toolbar_main_product)
        supportActionBar?.title = data.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
    }

    private fun onVisitProduct(url: String?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun onInquiry() {
        toast("enquiry in progress").show()
    }

    override fun onError(error: Throwable) {
        swipe_refresh_product.isRefreshing = false
        toast(error.localizedMessage).show()
    }

    override fun showView() {
        container_product_detail.visible()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}
