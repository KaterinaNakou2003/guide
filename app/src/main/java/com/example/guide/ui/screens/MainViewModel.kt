package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.network.placesApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PlacesApiUiState {
    data class Success(val photos: String) : PlacesApiUiState
    object Error : PlacesApiUiState
    object Loading : PlacesApiUiState
}

class MainViewModel() : ViewModel() {
    // The mutable State that stores the status of the most recent request
    var placesApiUiState: PlacesApiUiState by mutableStateOf(PlacesApiUiState.Loading)
        private set

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

    private fun getPlacesId1() {
        viewModelScope.launch {
        }
    }
}

