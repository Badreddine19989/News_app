package com.example.news.newsarticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.database.ArticleDatabaseDao
import com.example.news.database.Article

/**
 * Factory for [NewsArticleViewModel].
 */
class NewsArticleViewModelFactory(
    private val dataSource: ArticleDatabaseDao,
    private val article: Article
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsArticleViewModel::class.java)) {
            return NewsArticleViewModel(dataSource,article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
