package com.example.guide.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    val place_id: String,
    @ColumnInfo(name = "user_id", index = true)
    val user_id: Int,
    @ColumnInfo(name = "place_name")
    val place_name: String,
    @ColumnInfo(name = "place_address")
    val place_address: String,
    @ColumnInfo(name = "place_rating")
    val place_rating: Double,
    @ColumnInfo(name = "place_photo_reference")
    val place_photo_reference: String,
    @ColumnInfo(name = "place_type")
    val place_type: String


)