package com.example.guide.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "favorites",
    primaryKeys = ["place_id", "user_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )])
data class Favorite(
    @ColumnInfo(name = "place_id")
    val placeId: String,
    @ColumnInfo(name = "user_id", index = true)
    val userId: Int,
    @ColumnInfo(name = "place_name")
    val placeName: String,
    @ColumnInfo(name = "place_address")
    val placeAddress: String,
    @ColumnInfo(name = "place_rating")
    val placeRating: Double,
    @ColumnInfo(name = "place_photo_reference")
    val placePhotoReference: String,
    @ColumnInfo(name = "place_type")
    val placeType: String
)