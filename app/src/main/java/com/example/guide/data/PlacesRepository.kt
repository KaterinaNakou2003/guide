package com.example.guide.data

interface PlacesRepository {
    suspend fun getPlaceDetails(placeId: String): PlaceDetails
}