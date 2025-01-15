package com.example.guide.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guide.data.FavoritesRepository
import com.example.guide.data.UsersRepository
import com.example.guide.ui.screens.ProfileViewModel

class ProfileViewModelFactory(
    private val userId: Int,
    private val userRepository: UsersRepository,
    private val favoriteRepository: FavoritesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userId, userRepository, favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}