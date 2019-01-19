package id.bukusaku.bukusaku.ui.articles


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import cn.pedant.SweetAlert.SweetAlertDialog
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.R.id.action_search
import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.ui.detail.article.DetailArticleActivity
import id.bukusaku.bukusaku.utils.*
import kotlinx.android.synthetic.main.fragment_articles.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject


class ArticlesFragment : Fragment(), ArticlesContract.View {
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private lateinit var adapter: ArticlesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvArticles: RecyclerView
    private lateinit var rvSearch: RecyclerView
    private lateinit var alertError: SweetAlertDialog
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var searchView: SearchView? = null
    private var searchQuery: String? = null

    private val dataArticles: MutableList<Articles> = mutableListOf()
    private val presenter: ArticlePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_articles, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.find(R.id.articles_swipe_refresh)
        rvArticles = view.find(R.id.rv_articles)
        rvSearch = view.find(R.id.rv_search_product)

        initView()
        getData()
    }

    private fun getData() {
        onAttachView()
        presenter.getArticles()
    }

    private fun initView() {
        adapter = ArticlesAdapter(dataArticles) { startActivity<DetailArticleActivity>(ARTICLE_ID to it.id) }

        linearLayoutManager = LinearLayoutManager(activity)
        rvArticles.layoutManager = linearLayoutManager
        rvArticles.adapter = adapter
        setupScrollListener()

        rvSearch.layoutManager = LinearLayoutManager(activity)
        rvSearch.adapter = adapter

        alertError = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)

        swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    fun setupScrollListener(){
        rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount - 5
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount){
                    getData()
                }
            }
        })
    }

    override fun showArticles(data: List<Articles>) {
        swipeRefresh.isEnabled = true
        swipeRefresh.isRefreshing = false
        dataArticles.clear()
        dataArticles.addAll(data.sortedByDescending { it.id })
        adapter.notifyDataSetChanged()

        rvArticles.visible()
        rvSearch.gone()
        img_empty_search_articles.gone()
        tv_empty_search_articles.gone()
    }

    override fun onError(error: Throwable) {
        swipeRefresh.isRefreshing = false
        alertError.successOrFailed(
            error.localizedMessage +
                    "\n" + getString(R.string.home_lost_connection_message),
            getString(R.string.articles_error_alert_title),
            getString(R.string.articles_success_alert_confirm)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (null != searchItem) {
            searchView = searchItem.actionView as SearchView
            searchView?.queryHint = getString(R.string.articles_search_hint)
        }
        if (null != searchView) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return when {
                        query.isNullOrBlank() || query.isNullOrEmpty() -> {
                            getData()
                            true
                        }
                        else -> {
                            searchQuery = query
                            try {
                                presenter.getSearchResult(searchQuery?.toLowerCase())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            action_search -> {
                return false
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun showSearchResult(data: List<Articles>) {
        swipeRefresh.isEnabled = false
        rvArticles.gone()
        rvSearch.visible()
        img_empty_search_articles.gone()
        tv_empty_search_articles.gone()

        dataArticles.clear()
        dataArticles.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showEmpty() {
        swipeRefresh.isEnabled = false
        rvArticles.gone()
        rvSearch.gone()

        tv_empty_search_articles.text = getString(R.string.articles_empty_message, toUpperFirstWord(searchQuery))
        img_empty_search_articles.visible()
        tv_empty_search_articles.visible()
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