package id.bukusaku.bukusaku.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import id.bukusaku.bukusaku.data.local.entity.ProductEntity
import io.reactivex.Single

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(data: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE category LIKE :categoryName")
    fun getLocalProducts(categoryName: String): Single<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE name LIkE :productName AND category LIKE :category")
    fun getSearchResult(productName: String?, category: String?): List<ProductEntity>
}