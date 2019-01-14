package id.bukusaku.bukusaku.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import id.bukusaku.bukusaku.data.remote.ArticleDetail
import id.bukusaku.bukusaku.data.remote.ProductDetail

@Dao
interface BookmarkProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(data: ProductDetail)

    @Query("SELECT * FROM product_bookmark ORDER BY id DESC")
    fun getAllProductBookmark(): List<ProductDetail>

    @Query("SELECT * FROM product_bookmark WHERE id LIKE :id")
    fun getProductBookmark(id: Int): ProductDetail

    @Query("DELETE FROM product_bookmark WHERE id LIKE :id")
    fun deleteBookmark(id: Int)

    @Query("SELECT * FROM product_bookmark WHERE name LIKE :query")
    fun getSearchBookmarkResult(query: String?): List<ProductDetail>
}

@Dao
interface BookmarkArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(data: ArticleDetail)

    @Query("SELECT * FROM article_bookmark ORDER BY id ASC")
    fun getAllBookmark(): List<ArticleDetail>

    @Query("SELECT * FROM article_bookmark WHERE id LIKE :id")
    fun getArticleBookmark(id: Int): ArticleDetail

    @Query("DELETE FROM article_bookmark WHERE id LIKE :id")
    fun deleteBookmark(id: Int)

    @Query("SELECT * FROM article_bookmark WHERE title LIKE :query")
    fun getSearchBookmarkResult(query: String?): List<ArticleDetail>
}