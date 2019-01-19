package id.bukusaku.bukusaku.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.R.id.action_search
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.data.remote.ProductDetail
import id.bukusaku.bukusaku.ui.detail.article.DetailArticleActivity
import id.bukusaku.bukusaku.ui.detail.category.ProductsActivity
import id.bukusaku.bukusaku.ui.detail.product.ProductDetailActivity
import id.bukusaku.bukusaku.ui.home.adapter.CategoriesAdapter
import id.bukusaku.bukusaku.ui.home.adapter.LatestArticlesAdapter
import id.bukusaku.bukusaku.ui.home.adapter.SearchAdapter
import id.bukusaku.bukusaku.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject


class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var adapter: CategoriesAdapter
    private lateinit var adapterArticle: LatestArticlesAdapter
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var rvCategories: RecyclerView
    private lateinit var rvNews: RecyclerView
    private lateinit var rvSearch: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var latest: TextView
    private var searchQuery: String? = null
    private var searchView: SearchView? = null
    private lateinit var alertError: SweetAlertDialog
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    private var searchHome: MutableList<ProductDetail> = mutableListOf()
    private val categories: MutableList<Categories> = mutableListOf()
    private val articles: MutableList<NewArticles> = mutableListOf()
    private val presenter: HomePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCategories = view.find(R.id.rv_categories)
        rvNews = view.find(R.id.rv_update_news)
        rvSearch = view.find(R.id.rv_search)
        swipeRefresh = view.find(R.id.swipe_refresh)
        latest = view.find(R.id.tv_new_article)

        initView()
        getData()
    }

    private fun initView() {
        adapter = CategoriesAdapter(categories) { startActivity<ProductsActivity>(PRODUCT_NAME to it.name) }
        adapterArticle = LatestArticlesAdapter(articles) { startActivity<DetailArticleActivity>(ARTICLE_ID to it.id) }
        searchAdapter = SearchAdapter(searchHome) { startActivity<ProductDetailActivity>(PRODUCT_ID to it.id) }

        rvCategories.layoutManager = GridLayoutManager(activity, 3)
        rvCategories.adapter = adapter

        rvNews.layoutManager = LinearLayoutManager(activity)

        rvNews.adapter = adapterArticle

        rvSearch.layoutManager = LinearLayoutManager(activity)
        rvSearch.adapter = searchAdapter

        alertError = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
        swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    private fun getData() {
        onAttachView()
        presenter.getCategories()
        presenter.getNewArticles()
    }

    override fun showDataArticles(data: List<NewArticles>) {
        swipeRefresh.isRefreshing = false
        articles.clear()
        articles.addAll(data.sortedByDescending { it.id })
        adapterArticle.notifyDataSetChanged()
        showView()
    }

    override fun showDataCategories(data: List<Categories>) {
        swipeRefresh.isRefreshing = false
        categories.clear()
        categories.addAll(data.sortedBy { it.id })
        adapter.notifyDataSetChanged()
        showView()
    }

    private fun showView() {
        swipeRefresh.isEnabled = true
        rvNews.visible()
        rvCategories.visible()
        latest.visible()
        rvSearch.gone()
        img_empty_search.gone()
        tv_empty_search.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (null != searchItem) {
            searchView = searchItem.actionView as SearchView
            searchView?.queryHint = getString(R.string.home_search_hint)
        }
        if (null != searchView) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return when {
                        query.isNullOrBlank() || query.isNullOrEmpty() -> {
                            getData()
                            true
                        }
                        else -> {
                            searchQuery = query
                            searchQuery?.toLowerCase()
                            searchQuery = searchQuery?.replace(" ", "_")
                            presenter.getSearchResult(searchQuery)
                            true
                        }
                    }
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            action_search -> return false
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun showSearchResult(data: List<ProductDetail>) {
        swipeRefresh.isEnabled = false
        rvCategories.invisible()
        rvNews.gone()
        latest.invisible()
        rvSearch.visible()
        img_empty_search.gone()
        tv_empty_search.gone()
        searchHome.clear()
        searchHome.addAll(data)
        searchAdapter.notifyDataSetChanged()

    }

    override fun showEmpty() {
        swipeRefresh.isEnabled = false
        rvCategories.invisible()
        rvNews.gone()
        latest.invisible()
        rvSearch.gone()
        img_empty_search.visible()
        tv_empty_search.text = getString(R.string.products_empty_message, toUpperFirstWord(searchQuery))
        tv_empty_search.visible()
    }

    override fun onError(error: Throwable) {
        swipe_refresh.isRefreshing = false
        alertError.successOrFailed(
            error.localizedMessage +
                    "\n" + getString(R.string.home_lost_connection_message),
            getString(R.string.articles_error_alert_title),
            getString(R.string.articles_success_alert_confirm)
        )
    }

    override fun onErrorSearch(error: Throwable) {
        alertError.error(
            getString(R.string.articles_error_alert_title),
            getString(R.string.articles_error_alert_confirm),
            error.localizedMessage,
            getString(R.string.articles_error_alert_close)
        )
        alertError.setConfirmClickListener {
            presenter.getSearchResult(searchQuery)
            alertError.dismiss()
        }
        alertError.setCancelClickListener { alertError.dismiss() }
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
}