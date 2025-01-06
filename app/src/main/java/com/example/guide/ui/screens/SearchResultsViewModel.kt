package com.example.guide.ui.screens

import androidx.activity.result.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.network.Place
import kotlinx.coroutines.launch

class SearchResultsViewModel : ViewModel() {
    // For the result screen two states must be observed
    // 1. Query
    // 2. Search results
    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _searchResults = mutableStateOf<List<Place>>(emptyList())
    val searchResults: State<List<Place>> = _searchResults

    // Function to perform the search and update the query and search results
    fun searchPlaces(query: String) {
        _query.value = query // Update the query state
        viewModelScope.launch {
            // Make API call to Google Places API
            // Update _searchResults with the results
        }
    }
}