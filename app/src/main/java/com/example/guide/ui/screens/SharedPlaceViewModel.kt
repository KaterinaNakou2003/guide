package com.example.guide.ui.screens


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.guide.network.Place

class SharedPlaceViewModel : ViewModel() {
    private val _selectedPlace = mutableStateOf<Place?>(null)
    val selectedPlace: State<Place?> = _selectedPlace

    fun setSelectedPlace(place: Place) {
        _selectedPlace.value = place
    }
}