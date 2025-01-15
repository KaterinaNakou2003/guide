package com.example.guide.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guide.data.FavoritesRepository
import com.example.guide.ui.screens.DetailsViewModel
import com.example.guide.ui.screens.SharedPlaceViewModel

class DetailsViewModelFactory(
    private val userId: Int,
    private val sharedViewModel: SharedPlaceViewModel,
    private val favoritesRepository: FavoritesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            val place = sharedViewModel.selectedPlace.value
            if (place != null) {
                return DetailsViewModel(userId, place, favoritesRepository) as T
            } else {
                // Handle null value, e.g., throw an exception or provide a default value
                throw IllegalStateException("selectedPlace is null")
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}