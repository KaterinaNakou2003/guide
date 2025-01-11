package com.example.guide.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guide.ui.screens.SearchResultsViewModel

/*
* Factory class for SearchResultsViewModel.
*
 */
class SearchResultsViewModelFactory(private val query: String?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultsViewModel::class.java)) {
            return SearchResultsViewModel(query) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}