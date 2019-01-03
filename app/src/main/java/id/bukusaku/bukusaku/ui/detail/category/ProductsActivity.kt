package id.bukusaku.bukusaku.ui.detail.category

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailActivity
import id.bukusaku.bukusaku.utils.PRODUCT_ID
import id.bukusaku.bukusaku.utils.PRODUCT_NAME
import id.bukusaku.bukusaku.utils.visible
import kotlinx.android.synthetic.main.activity_products.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class ProductsActivity : AppCompatActivity(), ProductsContract.View {

    private val presenter: ProductsPresenter by inject()
    private val products: MutableList<ProductsMap> = mutableListOf()

    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        getProducts()
        initView()
    }

    private fun initView() {
        adapter = ProductsAdapter(products) {
            startActivity<ProductDetailActivity>(PRODUCT_ID to it.id)
        }
        rv_products.layoutManager = LinearLayoutManager(this@ProductsActivity)
        rv_products.adapter = adapter

        swipe_refresh_products.setOnRefreshListener { getProducts() }
    }

    private fun getProducts() {
        val category = intent.getStringExtra(PRODUCT_NAME)
        setSupportActionBar(toolbar_main_products)
        supportActionBar?.title = category
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.getProducts(category)
        onAttachView()
    }

    override fun showView() {
        rv_products.visible()
    }

    override fun showProducts(data: List<ProductsMap>) {
        swipe_refresh_products.isRefreshing = false
        products.clear()
        products.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onError(error: Throwable) {
        toast(error.localizedMessage).show()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}
