package com.example.guide.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Update
    suspend fun update(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * from favorites WHERE user_id = :userId and place_id = :placeId")
    fun getFavorite(userId: Int, placeId: Int): Flow<Favorite>

    @Query("SELECT * from favorites WHERE user_id = :id ORDER BY place_id ASC")
    fun getAllUserFavorites(id: Int): Flow<List<Favorite>>

    @Query("SELECT * from favorites ORDER BY place_id ASC")
    fun getAllFavorites(): Flow<List<Favorite>>
}