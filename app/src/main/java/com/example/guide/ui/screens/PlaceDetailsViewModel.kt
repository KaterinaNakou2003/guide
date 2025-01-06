package com.example.guide.ui.screens

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.data.Favorite
import com.example.guide.data.OfflineFavoritesRepository
import com.example.guide.data.OfflinePlacesRepository
import com.example.guide.data.PlaceDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceDetailsViewModel(
    private val placesRepository: OfflinePlacesRepository, // Replace with your actual repository
    private val favoritesRepository: OfflineFavoritesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<PlaceDetailsUiState>(PlaceDetailsUiState.Loading)
    val uiState: StateFlow<PlaceDetailsUiState> = _uiState

    fun getPlaceDetails(placeId: String) {
        viewModelScope.launch {
            _uiState.value = PlaceDetailsUiState.Loading
            _uiState.value = try {
                val placeDetails = placesRepository.getPlaceDetails(placeId) // Fetch details from API
                val isFavorite = favoritesRepository.getFavoriteStream(2,3)// Check favorite status in Room
                placeDetails.isFavorite = false // Random shit
                PlaceDetailsUiState.Success(placeDetails)
            } catch (e: Exception) {
                PlaceDetailsUiState.Error(e)
            }
        }
    }

    fun onFavoriteButtonClicked(place: PlaceDetails) {
        viewModelScope.launch {
            if (place.isFavorite) {
                favoritesRepository.deleteFavorite(Favorite(0,0))
            } else {
                favoritesRepository.insertFavorite(Favorite(0,0))
            }
            // Update uiState accordingly (e.g., refetch or update isFavorite)
        }
    }
}

sealed class PlaceDetailsUiState {
    object Loading : PlaceDetailsUiState()
    data class Success(val placeDetails: PlaceDetails) : PlaceDetailsUiState()
    data class Error(val exception: Exception) : PlaceDetailsUiState()
}