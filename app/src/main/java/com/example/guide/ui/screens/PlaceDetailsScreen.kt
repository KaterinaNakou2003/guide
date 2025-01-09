package com.example.guide.ui.screens

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guide.ui.AppViewModelProvider

@Composable
fun PlaceDetailsScreen(placeId: String, viewModel: PlaceDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = placeId) {
        viewModel.getPlaceDetails(placeId)
    }

    when (uiState) {
        is PlaceDetailsUiState.Loading -> {
            // Display loading indicator
        }
        is PlaceDetailsUiState.Success -> {
            val placeDetails = (uiState as PlaceDetailsUiState.Success).placeDetails
            // Display place details using placeDetails object
            // ...

            Button(onClick = { viewModel.onFavoriteButtonClicked(placeDetails) }) {
                // Display heart icon and text based on isFavorite status
            }
        }
        is PlaceDetailsUiState.Error -> {
            // Display error message
        }
    }
}