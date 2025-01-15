package com.example.guide.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Favorite] from a given data source.
 */
interface FavoritesRepository {
    /**
     * Retrieve all the favorites of a user from the the given data source that matches with the [userId].
     */
    fun getAllUserFavoritesStream(id: Int): Flow<List<Favorite>>

    /**
     * Retrieve a favorite of a user from the given data source that matches with [userId] and [placeId].
     */
    fun getFavoriteStream(userId: Int, placeId: String): Flow<Favorite?>

    /**
     * Insert favorite place in the data source
     */
    suspend fun insertFavorite(favorite : Favorite)

    /**
     * Delete favorite place from the data source
     */
    suspend fun deleteFavorite(favorite : Favorite)

    /**
     * Update favorite place in the data source
     */
    suspend fun updateFavorite(favorite : Favorite)
}