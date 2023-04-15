package com.example.news.savednews

import androidx.lifecycle.ViewModel
import com.example.news.database.ArticleDatabaseDao
import com.example.news.database.Article
import kotlinx.coroutines.*

/**
 * The [ViewModel] that is attached to the [SavedNews].
 */
class SavedNewsViewModel(dataSource: ArticleDatabaseDao) : ViewModel() {

    private val dataSource = dataSource

    //Creating the coroutine job and scope
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //Getting the articles from the database
    val articles = dataSource.getAllArticles()

    //Delete all articles
    fun deleteAllArticles() {
        coroutineScope.launch {
            deleteall()
        }
    }

    private suspend fun deleteall() {
        return withContext(Dispatchers.IO) {
            dataSource.clear()
        }
    }

    //Delete article
    fun deleteArticle(article: Article){
        coroutineScope.launch {
            delete(article)
        }
    }

    suspend private fun delete(article: Article) {
        return withContext(Dispatchers.IO){
            dataSource.deleteArticle(article.url)
        }
    }

    //onCleared method with coroutine job cancel
    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}