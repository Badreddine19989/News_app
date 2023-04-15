package com.example.news.breakingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.database.Article
import com.example.news.network.NewsApi
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * The [ViewModel] that is attached to the [BreakingNewsFragment].
 */
class BreakingNewsViewModel() : ViewModel() {

    //Encapsulated Live Data variable to store the API response
    private var _apiresponse = MutableLiveData<List<Article>>()
    val apiresponse: LiveData<List<Article>>
        get() = _apiresponse

    //Creating the coroutine job and scope
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //init block with api method call
    init {
        getNewsArticle()
    }

    //API method to get all articles
    fun getNewsArticle() {
        coroutineScope.launch {
            val getArticlesDeferred = NewsApi.retrofitService.getNewsArticle()
            try {
                val Result = getArticlesDeferred.await()
                if (Result.articles.size > 0) {
                    _apiresponse.value = Result.articles
                }
            } catch (e: Exception) {
                _apiresponse.value = ArrayList()
            }
        }
    }

    //onCleared method with coroutine job cancel
    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
