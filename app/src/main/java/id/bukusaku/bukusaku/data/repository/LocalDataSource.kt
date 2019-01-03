package id.bukusaku.bukusaku.data.repository

import id.bukusaku.bukusaku.data.local.dao.ArticlesDao
import id.bukusaku.bukusaku.data.local.dao.CategoriesDao
import id.bukusaku.bukusaku.data.local.dao.NewArticlesDao
import id.bukusaku.bukusaku.data.local.dao.ProductsDao
import id.bukusaku.bukusaku.data.local.entity.ArticlesEntity
import id.bukusaku.bukusaku.data.local.entity.CategoriesEntity
import id.bukusaku.bukusaku.data.local.entity.NewArticleEntity
import id.bukusaku.bukusaku.data.local.entity.ProductEntity
import id.bukusaku.bukusaku.data.map.ProductsMap
import io.reactivex.Single

class LocalDataSource(
    private val categoriesDao: CategoriesDao,
    private val newArticlesDao: NewArticlesDao,
    private val articlesDao: ArticlesDao,
    private val productsDao: ProductsDao
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

    fun getLocalProducts(categoryName:String):Single<List<ProductEntity>>{
        return productsDao.getLocalProducts(categoryName)
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

    fun saveProducts(data:List<ProductEntity>){
        return productsDao.insertProducts(data)
    }
}