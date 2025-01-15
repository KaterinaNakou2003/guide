package com.example.guide.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavoritesRepository: FavoritesRepository {

    private val fakeFavorites = mutableListOf<Favorite>(
        Favorite(placeId="xe", userId=1, placeName="placename", placeAddress="placeaddress",
                placeRating= 3.3, placePhotoReference="url", placeType="Type")
    )

    override fun getAllUserFavoritesStream(id: Int): Flow<List<Favorite>> = flow {
        TODO("Not yet implemented")
    }

    override fun getFavoriteStream(userId: Int, placeId: String): Flow<Favorite?> = flow {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavorite(favorite : Favorite) {
        TODO("Not yet implemented")
    }

    override  suspend fun deleteFavorite(favorite : Favorite) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavorite(favorite : Favorite) {
        TODO("Not yet implemented")
    }

}
