package com.example.guide.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Geometry(
    @SerialName("location") val location: Location,
    @SerialName("viewport") val viewport: Viewport
)

@Serializable
data class Location(
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double
)

@Serializable
data class Viewport(
    @SerialName("northeast") val northeast: Location,
    @SerialName("southwest") val southwest: Location
)

@Serializable
data class Photo(
    @SerialName("height") val height: Int,
    @SerialName("width") val width: Int,
    @SerialName("photo_reference") val photoReference: String,
    @SerialName("html_attributions") val htmlAttributions: List<String>
)

@Serializable
data class PlusCode(
    @SerialName("compound_code")val compoundCode: String,
    @SerialName("global_code")val globalCode: String
)


@Serializable
data class Review(
    @SerialName("author_name")val authorName: String,
    @SerialName("rating")val rating: Int,
    @SerialName("relative_time_description")val relativeTimeDescription: String,
    @SerialName("text")val text: String,
    @SerialName("time")val time: Int,
    @SerialName("translated")val translated: Boolean,
    @SerialName("profile_photo_url")val profilePhotoUrl: String,
    @SerialName("author_url")val authorUrl: String,
    @SerialName("language")val language: String,
    @SerialName("original_language")val originalLanguage: String
)

@Serializable
data class Result(
    @SerialName("business_status")val businessStatus: String,
    @SerialName("formatted_address")val formattedAddress: String,
    @SerialName("geometry")val geometry: Geometry,
    @SerialName("icon")val icon: String,
    @SerialName("name")val name: String,
    @SerialName("opening_hours")val openingHours: OpeningHours? = null,
    @SerialName("photos")val photos: List<Photo>? = null,
    @SerialName("place_id")val placeId: String,
    @SerialName("plus_code")val plusCode: PlusCode? = null,
    @SerialName("price_level")val priceLevel: Int? = null,
    @SerialName("rating")val rating: Double? = null,
    @SerialName("types")val types: List<String>,
    @SerialName("user_ratings_total")val userRatingsTotal: Int? = null,
    @SerialName("reviews")val reviews: List<Review>? = null
)

@Serializable
data class OpeningHours(
    @SerialName("open_now")val openNow: Boolean
)


@Serializable
data class Place(
    @SerialName("place_id") val placeId: String,
    @SerialName("formatted_address") val formattedAddress: String,
    @SerialName("name") val name: String,
    @SerialName("photo") val photos: List<Photo>?,
    @SerialName("rating") val rating: Double,
    @SerialName("types") val types: List<String>,
    @SerialName("reviews")val reviews: List<Review>? = null
)

@Serializable
data class PlacesResponse(
    @SerialName("results") val results: List<Result>,
    @SerialName("status") val status: String
) {
    // Transform results into candidates for easier usage
    val candidates: List<Place> = results.map {
        Place(
            placeId = it.placeId,
            formattedAddress = it.formattedAddress,
            name = it.name,
            photos = it.photos,
            types = it.types,
            reviews = it.reviews,
            rating = it.rating ?: 0.0 // Provide default rating if null
        )
    }
}

