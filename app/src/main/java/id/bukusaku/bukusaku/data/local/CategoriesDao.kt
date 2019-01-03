package id.bukusaku.bukusaku.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(data: List<CategoriesEntity>)

    @Query("SELECT * FROM categories")
    fun getLocalCategories(): Single<List<CategoriesEntity>>
}