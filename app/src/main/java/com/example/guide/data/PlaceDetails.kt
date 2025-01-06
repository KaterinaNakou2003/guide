package com.example.guide.data

data class PlaceDetails(
    val id: String,
    // ... other properties for basic info ...
    val description: String,
    // ... other properties for extra details ...
    var isFavorite: Boolean = false
)

/* No idea if this class is actually necessary
 * but i guess we'll see
 */