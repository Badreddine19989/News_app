package com.example.news.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
):Parcelable