package id.bukusaku.bukusaku.ui.bookmark.products


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.R.id.action_search
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailActivity
import id.bukusaku.bukusaku.utils.PRODUCT_ID
import id.bukusaku.bukusaku.utils.gone
import id.bukusaku.bukusaku.utils.toUpperFirstWord
import id.bukusaku.bukusaku.utils.visible
import kotlinx.android.synthetic.main.fragment_product_bokmark.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject

class BookmarkProductFragment : Fragment(), BookmarkProductContract.View {
    private lateinit var rvBookmark: RecyclerView
    private lateinit var rvSearch: RecyclerView
    private lateinit var adapter: BookmarkProductAdapter
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    private var querySearch: String? = null
    private var searchView: SearchView? = null
    private var data: MutableList<ProductDetail> = mutableListOf()
    private val presenter: BookmarkProductPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_product_bokmark, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBookmark = view.find(R.id.rv_product_bookmark)
        rvSearch = view.find(R.id.rv_product_bookmark_search)

        initView()
        getData()
    }

    private fun getData() {
        presenter.getProductBookmark()
        onAttachView()
    }

    private fun initView() {
        adapter = BookmarkProductAdapter(data) {
            startActivity<ProductDetailActivity>(PRODUCT_ID to it.id)
        }

        rvBookmark.layoutManager = LinearLayoutManager(activity)
        rvBookmark.adapter = adapter

        rvSearch.layoutManager = LinearLayoutManager(activity)
        rvSearch.adapter = adapter
    }

    override fun onLoadData(data: List<ProductDetail>?) {
        this.data.clear()
        data?.let { this.data.addAll(it) }
        adapter.notifyDataSetChanged()

        img_empty_product.gone()
        tv_empty_product.gone()
        rvSearch.gone()
        rvBookmark.visible()
    }

    override fun showEmpty() {
        img_empty_product.visible()
        tv_empty_product.text = getString(R.string.empty_message)
        tv_empty_product.visible()
        rvBookmark.gone()
        rvSearch.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val sm: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (null != searchItem) {
            searchView = searchItem.actionView as SearchView
            searchView?.queryHint = getString(R.string.bookmark_products_search_hint)
        }
        if (null != searchView) {
            searchView?.setSearchableInfo(sm.getSearchableInfo(activity?.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return when {
                        p0.isNullOrBlank() || p0.isNullOrEmpty() -> {
                            getData()
                            true
                        }
                        else -> {
                            try {
                                querySearch = p0
                                presenter.getSearchResult(querySearch?.toLowerCase())
                            } catch (e: Throwable) {
                                rvSearch.snackbar(getString(R.string.articles_search_failed, e.localizedMessage)).show()
                            }
                            true
                        }
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
                getData()
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            action_search -> return false
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun showSearchResult(data: List<ProductDetail>) {
        rvBookmark.gone()
        img_empty_product.gone()
        tv_empty_product.gone()
        rvSearch.visible()

        this.data.clear()
        this.data.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNotFound() {
        rvBookmark.gone()
        rvSearch.gone()

        tv_empty_product.text = getString(R.string.products_bookmark_empty_message, toUpperFirstWord(querySearch))
        img_empty_product.visible()
        tv_empty_product.visible()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }
}