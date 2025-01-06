package com.example.guide.data

import kotlinx.coroutines.flow.Flow

class OfflineFavoritesRepository(private val favoriteDao: FavoriteDao) : FavoritesRepository {
    override fun getAllFavoritesStream(): Flow<List<Favorite>> = favoriteDao.getAllFavorites()

    override fun getAllUserFavoritesStream(id: Int): Flow<List<Favorite>> = favoriteDao.getAllUserFavorites(id)

    override fun getFavoriteStream(userId: Int, placeId: Int): Flow<Favorite?> = favoriteDao.getFavorite(userId, placeId)

    override suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insert(favorite)

    override suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.delete(favorite)

    override suspend fun updateFavorite(favorite: Favorite) = favoriteDao.update(favorite)
}