package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.data.Favorite
import com.example.guide.data.FavoritesRepository
import com.example.guide.network.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val userID: Int,
    private val placeSelected: Place,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    // Assuming you have a Place object
    val place: StateFlow<Place?> = MutableStateFlow(placeSelected)

    // User ID
    val userId = mutableStateOf(userID)

    // Favorite button click state
    private val _isPlaceFavorite = MutableStateFlow(false)
    val isPlaceFavorite: StateFlow<Boolean> = _isPlaceFavorite.asStateFlow()

    fun onFavoriteButtonClick(place: Place) {
        val favorite = Favorite(place.placeId, userId.value, place.name, place.formattedAddress, place.rating, place.photos?.firstOrNull()?.photoReference?: "", place.types.firstOrNull() ?:"")
        // If the place is already included in the favorites
        if (_isPlaceFavorite.value) {
            // Remove the place from favorites
            viewModelScope.launch {
                favoritesRepository.deleteFavorite(favorite)
            }

        } else {
            // Add the place to favorites
            viewModelScope.launch {
                favoritesRepository.insertFavorite(favorite)
            }
        }
        // Toggle favorite status and update UI
        _isPlaceFavorite.value = !_isPlaceFavorite.value

    }

    init {
        viewModelScope.launch {
            // Check if the place is already a favorite for the user
            val isFavorite = favoritesRepository.getFavoriteStream(userId.value, placeSelected.placeId).firstOrNull() != null
            _isPlaceFavorite.value = isFavorite
        }
    }
}