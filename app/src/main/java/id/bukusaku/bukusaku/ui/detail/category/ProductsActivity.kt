package id.bukusaku.bukusaku.ui.detail.category

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.ProductsMap
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailActivity
import id.bukusaku.bukusaku.utils.*
import kotlinx.android.synthetic.main.activity_products.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject

class ProductsActivity : AppCompatActivity(), ProductsContract.View {
    private val presenter: ProductsPresenter by inject()
    private val products: MutableList<ProductsMap> = mutableListOf()

    private var searchQuery: String? = null
    private var searchView: SearchView? = null

    private lateinit var categoryName: String
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
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

        rv_search_products.layoutManager = LinearLayoutManager(this@ProductsActivity)
        rv_search_products.adapter = adapter

        swipe_refresh_products.setOnRefreshListener { getProducts() }
    }

    private fun getProducts() {
        categoryName = intent.getStringExtra(PRODUCT_NAME)
        setSupportActionBar(toolbar_main_products)
        supportActionBar?.title = categoryName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.getProducts(categoryName)
        onAttachView()
    }

    override fun showView() {
        swipe_refresh_products.isEnabled = true
        rv_products.visible()
        rv_search_products.gone()
    }

    override fun showProducts(data: List<ProductsMap>) {
        swipe_refresh_products.isRefreshing = false
        products.clear()
        products.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager: SearchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (null != searchItem) {
            searchView = searchItem.actionView as SearchView
            searchView?.queryHint = getString(R.string.category_search_hint,categoryName.toLowerCase())
        }
        if (null != searchView) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return when {
                        null != query -> {
                            searchQuery = query
                            try {
                                presenter.getProductsSearchResult(searchQuery?.toLowerCase(), categoryName)
                            } catch (e: Throwable) {
                                rv_search_products.snackbar(getString(R.string.articles_search_failed,e.localizedMessage)).show()
                            }
                            true
                        }
                        query.isNullOrBlank() -> {
                            getProducts()
                            true
                        }
                        else -> false
                    }
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                getProducts()
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> return false
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun showSearchResult(data: List<ProductsMap>) {
        products.clear()
        products.addAll(data)
        adapter.notifyDataSetChanged()

        swipe_refresh_products.isEnabled = false
        rv_products.gone()
        rv_search_products.visible()
        img_empty_search_products.gone()
        tv_empty_search_products.gone()

    }

    override fun showEmpty() {
        swipe_refresh_products.isEnabled = false
        tv_empty_search_products.text = getString(R.string.product_empty_message, toUpperFirstWord(searchQuery))
        rv_products.gone()
        rv_search_products.gone()
        img_empty_search_products.visible()
        tv_empty_search_products.visible()
    }


    override fun onError(error: Throwable) {
        rv_search_products.snackbar(error.localizedMessage).show()
    }

    override fun onAttachView() { presenter.onAttach(this) }

    override fun onDetachView() { presenter.onDetach() }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}
