package id.bukusaku.bukusaku.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface NewArticlesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewArticles(data:List<NewArticleEntity>)

    @Query("SELECT * FROM new_articles")
    fun getLocalNewArticles():Single<List<NewArticleEntity>>
}

@Dao
interface ArticlesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(data:List<ArticlesEntity>)

    @Query("SELECT * FROM articles")
    fun getLocalArticles():Single<List<ArticlesEntity>>
}