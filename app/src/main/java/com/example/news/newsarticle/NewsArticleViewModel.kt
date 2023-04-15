package com.example.news.newsarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.database.ArticleDatabaseDao
import com.example.news.database.Article
import kotlinx.coroutines.*

/**
 * The [ViewModel] that is attached to the [NewsArticleFragment].
 */
class NewsArticleViewModel(
    dataSource: ArticleDatabaseDao, article: Article
) : ViewModel() {
    private val dataSource = dataSource
    private var article = article

    //Creating the coroutine job and scope
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //Live Data variable to trigger a Snackbar event
    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    //After the Snackbar event is triggered
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    //Save Article
    fun saveArticle() {
        uiScope.launch {
            saveThisArtile()
        }
        _showSnackbarEvent.value = true
    }

    private suspend fun saveThisArtile() {
        return withContext(Dispatchers.IO) {
            dataSource.upsert(article)
        }
    }

    //Function to return article url for sharing
    fun getArticleUrl() : String{
        return article.url
    }

    //onCleared method with coroutine job cancel
    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}