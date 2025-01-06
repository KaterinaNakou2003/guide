package com.example.guide.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )])
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "place_id")
    val place_id: Int = 0,
    @ColumnInfo(name = "user_id")
    val user_id: Int
)