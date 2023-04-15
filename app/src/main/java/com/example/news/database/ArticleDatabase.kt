package com.example.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(TypeConvertion::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val ArticleDatabaseDao: ArticleDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        //Get the database instance
        fun getInstance(context: Context): ArticleDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase::class.java,
                        "article_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}