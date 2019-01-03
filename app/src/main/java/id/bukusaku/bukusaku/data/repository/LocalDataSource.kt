package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.local.*
import io.reactivex.Single

class LocalDataSource(
    private val categoriesDao: CategoriesDao,
    private val newArticlesDao: NewArticlesDao,
    private val articlesDao: ArticlesDao
) {
    fun getLocalCategories(): Single<List<CategoriesEntity>> {
        return categoriesDao.getLocalCategories()
    }

    fun getLocalNewArticle(): Single<List<NewArticleEntity>> {
        return newArticlesDao.getLocalNewArticles()
    }

    fun getLocalArticle(): Single<List<ArticlesEntity>> {
        return articlesDao.getLocalArticles()
    }

    fun saveCategories(data: List<CategoriesEntity>) {
        categoriesDao.insertCategories(data)
    }

    fun saveNewArticles(data: List<NewArticleEntity>) {
        newArticlesDao.insertNewArticles(data)
    }

    fun saveArticles(data: List<ArticlesEntity>) {
        articlesDao.insertArticles(data)
    }
}