package com.example.guide.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlacesResponse(
    @SerialName("candidates") val candidates: List<Place>,
    @SerialName("status") val status: String
)

@Serializable
data class Place(
    @SerialName("place_id") val placeId: String, // Might be useful for specific place lookups
    @SerialName("formatted_address") val formattedAddress: String,
    @SerialName("name") val name: String,
    @SerialName("photo") val photos: List<Photo>?,
    @SerialName("rating") val rating: Double,
    @SerialName("types") val types: List<String>

)

@Serializable
data class Photo(
    @SerialName("height") val height: Int,
    @SerialName("width") val width: Int,
    @SerialName("photo_reference") val photoReference: String
)
