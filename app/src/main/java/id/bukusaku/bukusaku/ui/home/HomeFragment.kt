package id.bukusaku.bukusaku.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.utils.visible
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber


class HomeFragment : Fragment(), MainContract.View {
    private lateinit var adapter: MainAdapter
    private lateinit var adapterArticle: ArticleNewAdapter

    private lateinit var rvCategories: RecyclerView
    private lateinit var rvNews: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var latest: TextView

    private val categories: MutableList<Categories> = mutableListOf()
    private val articles: MutableList<NewArticles> = mutableListOf()

    private val presenter: MainPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCategories = view.find(R.id.rv_categories)
        rvNews = view.find(R.id.rv_update_news)
        swipeRefresh = view.find(R.id.swipe_refresh)
        latest = view.find(R.id.tv_new_article)

        initView()
        getData()
    }

    private fun showView() {
        rvCategories.visible()
        rvNews.visible()
        latest.visible()
    }

    private fun initView() {
        adapter = MainAdapter(categories) { categories ->
            toast(categories.name.toString()).show()
        }
        adapterArticle = ArticleNewAdapter(articles) { article ->
            toast(article.title.toString()).show()
        }

        rvCategories.layoutManager = GridLayoutManager(activity, 3)
        rvCategories.adapter = adapter

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        rvNews.layoutManager = layoutManager
        rvNews.adapter = adapterArticle

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
        Timber.d("data articles: ${Gson().toJsonTree(data)}")
        swipeRefresh.isRefreshing = false
        articles.clear()
        articles.addAll(data)
        adapterArticle.notifyDataSetChanged()
        showView()
    }

    override fun showDataCategories(data: List<Categories>) {
        Timber.d("data categories: ${Gson().toJsonTree(data)}")
        swipeRefresh.isRefreshing = false
        categories.clear()
        categories.addAll(data)
        adapter.notifyDataSetChanged()
        showView()
    }

    override fun onError(error: Throwable) {
        Timber.e(error.localizedMessage)
        rvCategories.snackbar(error.localizedMessage)
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
