package com.example.news.savednews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.database.ArticleDatabaseDao

/**
 * Factory for [SavedNewsViewModel].
 */
class SavedNewsViewModelFactory(
    private val dataSource: ArticleDatabaseDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedNewsViewModel::class.java)) {
            return SavedNewsViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}