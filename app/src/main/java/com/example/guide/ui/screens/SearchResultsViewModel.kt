package com.example.guide.ui.screens

import androidx.activity.result.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.network.Place
import com.example.guide.network.PlacesApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PlacesApiUiState {
    object Success: PlacesApiUiState
    object Error : PlacesApiUiState
    object Loading : PlacesApiUiState
}

class SearchResultsViewModel(
    query: String?
) : ViewModel() {

    // State for the search query
    private val _query = mutableStateOf("")
    val query: State<String> = _query

    // State for the list of search results
    private val _searchResults = mutableStateOf<List<Place>>(emptyList())
    val searchResults: State<List<Place>> = _searchResults

    /** The mutable State that stores the status of the most recent request */
    var placesApiUiState: PlacesApiUiState by mutableStateOf(PlacesApiUiState.Loading)
        private set

    // Function to perform the search and update the query and search results
    fun searchPlaces(query: String) {
        _query.value = query // Update the query state
        // Make API call to Google Places API
        /*
        viewModelScope.launch {
                try {
                    val response = PlacesApi.retrofitService.findPlaceFromText(query).execute()
                    // Check if the response was successful
                    if (response.isSuccessful) {
                        placesApiUiState = PlacesApiUiState.Success
                        val placesResponse = response.body()
                        _searchResults.value = placesResponse?.candidates ?: emptyList()
                    } else {
                        placesApiUiState = PlacesApiUiState.Error
                    }
                } catch (e: IOException) {
                    placesApiUiState = PlacesApiUiState.Error
                }
        }

         */
    }

    init {
        if (query != null) {
            searchPlaces(query)
        }
    }
}