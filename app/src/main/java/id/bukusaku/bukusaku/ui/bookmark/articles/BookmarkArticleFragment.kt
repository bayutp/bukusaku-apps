package id.bukusaku.bukusaku.ui.bookmark.articles


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
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.ui.detail.article.DetailArticleActivity
import id.bukusaku.bukusaku.utils.ARTICLE_ID
import id.bukusaku.bukusaku.utils.gone
import id.bukusaku.bukusaku.utils.toUpperFirstWord
import id.bukusaku.bukusaku.utils.visible
import kotlinx.android.synthetic.main.fragment_article_bookmark.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject

class BookmarkArticleFragment : Fragment(), BookmarkArticleContract.View {

    private lateinit var rvSearch: RecyclerView
    private lateinit var rvBookmark: RecyclerView
    private lateinit var adapter: BookmarkArticleAdapter
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    private var searchQuery: String? = null
    private var searchView: SearchView? = null
    private var data: MutableList<ArticleDetail> = mutableListOf()

    private val presenter: BookmarkArticlePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_article_bookmark, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBookmark = view.find(R.id.rv_article_bookmark)
        rvSearch = view.find(R.id.rv_article_bookmark_search)

        initView()
        getData()
    }

    private fun getData() {
        presenter.getAllArticles()
        onAttachView()
    }

    private fun initView() {
        adapter = BookmarkArticleAdapter(data) {
            startActivity<DetailArticleActivity>(ARTICLE_ID to it.id)
        }

        rvBookmark.layoutManager = LinearLayoutManager(activity)
        rvBookmark.adapter = adapter

        rvSearch.layoutManager = LinearLayoutManager(activity)
        rvSearch.adapter = adapter
    }

    override fun onLoadArticles(data: List<ArticleDetail>?) {
        this.data.clear()
        data?.let { this.data.addAll(it) }
        adapter.notifyDataSetChanged()

        img_empty.gone()
        tv_empty.gone()
        rvBookmark.visible()
        rvSearch.gone()
    }

    override fun showEmpty() {
        img_empty.visible()
        tv_empty.text = getString(R.string.empty_message)
        tv_empty.visible()
        rvBookmark.gone()
        rvSearch.gone()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val sm: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (null != searchItem) {
            searchView = searchItem.actionView as SearchView
            searchView?.queryHint = getString(R.string.bookmark_articles_search_hint)
        }
        if (null != searchView) {
            searchView?.setSearchableInfo(sm.getSearchableInfo(activity?.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return when {
                        p0.isNullOrEmpty() || p0.isNullOrBlank() -> {
                            getData()
                            true
                        }
                        else -> {
                            try {
                                searchQuery = p0
                                presenter.getSearchResult(searchQuery?.toLowerCase())
                            } catch (e: Throwable) {
                                rvSearch.snackbar(R.string.articles_search_failed).show()
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

    override fun showSearchResult(data: List<ArticleDetail>) {
        rvBookmark.gone()
        img_empty.gone()
        tv_empty.gone()
        rvSearch.visible()
        this.data.clear()
        this.data.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNotFound() {
        rvBookmark.gone()
        rvSearch.gone()

        tv_empty.text = getString(R.string.articles_bookmark_empty_message, toUpperFirstWord(searchQuery))
        img_empty.visible()
        tv_empty.visible()
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
