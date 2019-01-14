package id.bukusaku.bukusaku.ui.bookmark.articles

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.utils.loadImageArticle
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_articles.*

class BookmarkArticleAdapter(
    private val data: MutableList<ArticleDetail>,
    private val listener: (ArticleDetail) -> Unit
) : RecyclerView.Adapter<BookmarkArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_articles, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: ArticleDetail, listener: (ArticleDetail) -> Unit) {
            img_articles.loadImageArticle(data.imageUrl)
            tv_articles.text = data.title
            tv_category.text = data.categories.name
            tv_category.setTextColor(Color.parseColor(data.categories.color))
            tv_desc.text = data.content
            itemView.setOnClickListener { listener(data) }
        }
    }
}