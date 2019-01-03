package id.bukusaku.bukusaku.ui.detail.article

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.utils.ARTICLE_ID
import id.bukusaku.bukusaku.utils.loadImage
import id.bukusaku.bukusaku.utils.visible
import kotlinx.android.synthetic.main.activity_detail_article.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class DetailArticleActivity : AppCompatActivity(), ArticleDetailContract.View {
    private val presenter: ArticleDetailPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        getData()
        initView()
    }

    private fun initView() {
        swipe_refresh_detail.setOnRefreshListener { getData() }
        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getData() {
        val id = intent.getIntExtra(ARTICLE_ID, 0)
        presenter.getArticleById(id)
        onAttachView()
    }

    override fun showArticle(data: ArticleDetail) {
        swipe_refresh_detail.isRefreshing = false
        img_detail.loadImage(data.imageUrl)
        tv_title_detail.text = data.title
        tv_category_detail.text = data.categories.name
        tv_category_detail.setTextColor(Color.parseColor(data.categories.color))
        tv_content_detail.text = data.content
        tv_link.text = "Sumber artikel : ${data.link}"
        tv_link.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.link)))
        }
    }

    override fun showView() {
        toolbar_detail.visible()
        tv_title_detail.visible()
        tv_category_detail.visible()
        tv_content_detail.visible()
        tv_link.visible()
    }

    override fun onError(error: Throwable) {
        swipe_refresh_detail.isRefreshing = false
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

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}
