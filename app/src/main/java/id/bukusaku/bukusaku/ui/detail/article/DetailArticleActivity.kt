package id.bukusaku.bukusaku.ui.detail.article

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import cn.pedant.SweetAlert.SweetAlertDialog
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.R.id.menu_bookmark_article
import id.bukusaku.bukusaku.R.id.menu_link
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.utils.*
import kotlinx.android.synthetic.main.activity_detail_article.*
import org.koin.android.ext.android.inject

class DetailArticleActivity : AppCompatActivity(), ArticleDetailContract.View {
    private lateinit var alertLoading: SweetAlertDialog
    private lateinit var alertError: SweetAlertDialog
    private lateinit var alertBookmarkSuccess : SweetAlertDialog
    private lateinit var alertBookmarkFailed : SweetAlertDialog

    private var isBookmark: Boolean = false
    private var data: ArticleDetail? = null
    private var idArticle: Int = 0
    private var link: String? = null
    private var menu: Menu? = null
    private val presenter: ArticleDetailPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        initView()
        initAlert()
        getData()
    }

    private fun initView() {
        setSupportActionBar(toolbar_detail)
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAlert() {
        alertLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        alertError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        alertBookmarkSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        alertBookmarkFailed = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        showLoading()
    }

    private fun getData() {
        idArticle = intent.getIntExtra(ARTICLE_ID, 0)
        presenter.getArticleById(idArticle)
        presenter.statusBookmark(idArticle)
        onAttachView()
    }

    override fun showArticle(data: ArticleDetail) {
        this.data = data

        img_detail.loadImage(data.imageUrl)
        tv_title_detail.text = data.title
        tv_category_detail.text = data.categories.name
        tv_category_detail.setTextColor(Color.parseColor(data.categories.color))
        tv_content_detail.text = data.content
        link = data.link
    }

    override fun showView() {
        alertLoading.dismiss()
        toolbar_detail.visible()
        tv_title_detail.visible()
        tv_category_detail.visible()
        tv_content_detail.visible()
    }

    override fun onError(error: Throwable) {
        alertLoading.dismiss()
        val title = getString(R.string.articles_error_alert_title)
        val confirm = getString(R.string.articles_error_alert_confirm)
        val cancel = getString(R.string.articles_error_alert_close)
        alertError.setConfirmClickListener { getData() }
        alertError.setCancelClickListener { this.finish() }
        alertError.error(title,confirm,error.localizedMessage,cancel)

    }

    override fun showLoading() {
        alertError.dismiss()
        alertLoading.loading(getString(R.string.articles_alert_loading_title))
    }

    private fun showSuccess(message:String, title:String){
        alertBookmarkFailed.dismiss()
        alertBookmarkSuccess.successOrFailed(message,title,getString(R.string.articles_success_alert_confirm))
    }

    private fun showFailed(message: String){
        alertBookmarkSuccess.dismiss()
        alertBookmarkFailed.successOrFailed(message,
            getString(R.string.articles_error_alert_title),
            getString(R.string.articles_success_alert_confirm)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_detail_menu, menu)
        this.menu = menu
        setBookmark()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            menu_bookmark_article -> {
                if (isBookmark) removeFromBookmark() else addToBookmark()
                isBookmark = !isBookmark
                setBookmark()
                true
            }
            menu_link -> {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent: CustomTabsIntent = builder.build()
                builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                builder.addDefaultShareMenuItem()
                customTabsIntent.launchUrl(this, Uri.parse(link))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showStatus(data: ArticleDetail?) {
        if (data != null) {
            isBookmark = true
            setBookmark()
        }
    }

    private fun setBookmark() {
        if (isBookmark) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_border)
        }
    }

    private fun addToBookmark() {
        try {
            val message = getString(R.string.bookmark_articles_success_message)
            val title = getString(R.string.bookmark_articles_success_title)
            data?.let { presenter.addToBookmark(it) }
            showSuccess(message,title)
        } catch (e: Throwable) {
           showFailed(getString(R.string.bookmark_articles_error,e.localizedMessage))
        }
    }

    private fun removeFromBookmark() {
        try {
            val message = getString(R.string.delete_bookmark_articles_message)
            val title = getString(R.string.delete_bookmark_articles_title)
            idArticle.let { presenter.deleteFromBookmark(it) }
            showSuccess(message,title)
        } catch (e: Throwable) {
            showFailed(getString(R.string.delete_bookmark_articles_error,e.localizedMessage))
        }
    }

    override fun onAttachView() { presenter.onAttach(this) }

    override fun onDetachView() { presenter.onDetach() }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}