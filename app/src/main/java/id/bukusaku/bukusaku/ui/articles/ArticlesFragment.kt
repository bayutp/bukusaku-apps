package id.bukusaku.bukusaku.ui.articles


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.Articles
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject


class ArticlesFragment : Fragment(), ArticlesContract.View {
    private val dataArticles: MutableList<Articles> = mutableListOf()
    private val presenter: ArticlePresenter by inject()

    private lateinit var adapter: ArticlesAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvArticles: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.find(R.id.articles_swipe_refresh)
        rvArticles = view.find(R.id.rv_articles)

        initView()
        getData()
    }

    private fun getData() {
        onAttachView()
        presenter.getArticles()
    }

    private fun initView() {
        adapter = ArticlesAdapter(dataArticles, { data ->
            toast(data.title.toString()).show()
        }) { dataBookmark ->
            toast(dataBookmark.imageUrl.toString()).show()
        }

        layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rvArticles.layoutManager = layoutManager
        rvArticles.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    override fun showArticles(data: List<Articles>) {
        swipeRefresh.isRefreshing = false
        dataArticles.clear()
        dataArticles.addAll(data)
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

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }
}
