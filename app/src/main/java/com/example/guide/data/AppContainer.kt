package com.example.guide.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val favoritesRepository: FavoritesRepository
    val usersRepository: UsersRepository
}

/**
 * [AppContainer] implementation that provides a. instance of [OfflineFavoritesRepository]
 *  and b. instances of [OfflineUsersRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [FavoritesRepository]
     */
    override val favoritesRepository: FavoritesRepository by lazy {
        OfflineFavoritesRepository(GuideDatabase.getDatabase(context).favoriteDao())
    }
    /**
     * Implementation for [UsersRepository]
     */
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(GuideDatabase.getDatabase(context).userDao())
    }
}