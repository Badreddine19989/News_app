package com.example.news.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(article: Article)

    @Query("SELECT * from article")
    fun getAllArticles(): LiveData<List<Article?>>

    @Query("DELETE from article WHERE url = :url")
    fun deleteArticle(url: String)

    @Query("DELETE FROM article")
    fun clear()
}