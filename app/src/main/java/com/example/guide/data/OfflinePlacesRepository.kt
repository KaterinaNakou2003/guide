package com.example.guide.data

class OfflinePlacesRepository : PlacesRepository {
    override suspend fun getPlaceDetails(placeId: String): PlaceDetails {
        // Make API call to Google Places API using placeId
        // ...
        // Return PlaceDetails object
        // Dummy return to make it shut up
        return PlaceDetails("ffsf", "ffsfssf")
    }
}