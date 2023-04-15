package com.example.news.network

import com.example.news.database.NewsArticle
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    //Method to call all articles
    @GET("top-headlines")
    fun getNewsArticle(
        @Query("country") country: String = "us",
        @Query("page") page: String = "1",
        @Query("apiKey") APIKEY: String = "f98e5a7546e440c4818fc1ce64dde45a"
    ):
            Deferred<NewsArticle>

    //Method to call searched articles
    @GET("top-headlines")
    fun searchNews(
        @Query("q") q: String,
        @Query("page") page: String = "1",
        @Query("apiKey") APIKEY: String = "f98e5a7546e440c4818fc1ce64dde45a"
    ):
            Deferred<NewsArticle>
}

object NewsApi {
    val retrofitService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}