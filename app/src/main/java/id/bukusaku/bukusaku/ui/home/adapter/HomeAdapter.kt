package id.bukusaku.bukusaku.ui.home.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.bukusaku.bukusaku.R
import id.bukusaku.bukusaku.data.map.Categories
import id.bukusaku.bukusaku.data.map.NewArticles
import id.bukusaku.bukusaku.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_categories.*
import kotlinx.android.synthetic.main.item_new_article.*

class CategoriesAdapter(private val categories: MutableList<Categories>, private val listener: (Categories) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categories, parent, false)
    )

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(category: Categories, listener: (Categories) -> Unit) {
            img_category.loadImage(category.icon)
            tv_categories.text = category.name
            itemView.setOnClickListener { listener(category) }
        }
    }
}

class LatestArticlesAdapter(
    private val articles: MutableList<NewArticles>,
    private val listener: (NewArticles) -> Unit
) : RecyclerView.Adapter<LatestArticlesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_new_article, parent, false)
    )

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(article: NewArticles, listener: (NewArticles) -> Unit) {
            img_cover_new.loadImage(article.imageUrl)
            tv_category_new.text = article.category.name
            tv_category_new.setBackgroundColor(Color.parseColor(article.category.color))
            tv_title_new.text = article.title
            itemView.setOnClickListener { listener(article) }
        }
    }
}