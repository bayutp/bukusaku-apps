package id.bukusaku.bukusaku.ui.articles

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.Articles
import id.bukusaku.bukusaku.utils.loadImage
import id.bukusaku.bukusaku.utils.loadImageArticle
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.abc_popup_menu_item_layout.*
import kotlinx.android.synthetic.main.item_articles.*
import kotlinx.android.synthetic.main.item_articles.view.*

class ArticlesAdapter(
    private val articles: MutableList<Articles>,
    private val listener: (Articles) -> Unit,
    private val listenerBookmark: (Articles) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_articles, parent, false))
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position], listener, listenerBookmark)
    }

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(article: Articles, listener: (Articles) -> Unit, listenerBookmark: (Articles) -> Unit) {
            img_articles.loadImageArticle(article.imageUrl)
            tv_articles.text = article.title
            tv_category.text = article.category.name
            tv_category.setTextColor(Color.parseColor(article.category.color))
            tv_desc.text = article.content
            itemView.img_bookmark.setOnClickListener { listenerBookmark(article) }
            itemView.setOnClickListener { listener(article) }
        }
    }
}