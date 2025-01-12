package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class MainViewModel() : ViewModel() {

    // State for the search bar
    var searchQuery by mutableStateOf("")
        private set

    // Updates the search query
    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
    }

    // Handles button click events
    fun onButtonClick(buttonName: String) {
        searchQuery = buttonName
    }

}

