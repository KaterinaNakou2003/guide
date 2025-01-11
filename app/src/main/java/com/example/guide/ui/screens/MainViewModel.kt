package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class MainViewModel() : ViewModel() {
    // The mutable State that stores the status of the most recent request
    //var placesApiUiState: PlacesApiUiState by mutableStateOf(PlacesApiUiState.Loading)
    //    private set LINES TO BE DELETED

    // State for the search bar
    var searchQuery by mutableStateOf("")
        private set


    // State for the filtered list
    private val _items = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grapes")
    val filteredItems: List<String>
        get() = _items.filter { it.contains(searchQuery, ignoreCase = true) }

    // State for button click actions
    var buttonMessage by mutableStateOf("")
        private set

    // Updates the search query
    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
    }

    // Handles button click events
    fun onButtonClick(buttonName: String) {
        buttonMessage = "You clicked $buttonName!"
    }

}

