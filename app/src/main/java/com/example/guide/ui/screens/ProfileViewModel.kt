package com.example.guide.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.data.Favorite
import com.example.guide.data.FavoritesRepository
import com.example.guide.data.UsersRepository
import com.example.guide.network.Place
import com.example.guide.network.PlacesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.io.IOException

class ProfileViewModel(
    private val userID: Int,
    private val userRepository: UsersRepository,
    val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val userId = mutableStateOf(userID)

    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var email = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    val originalUsername = mutableStateOf("")
    val originalPassword = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var successMessage = mutableStateOf("")

    init {
        viewModelScope.launch {
            // Fetch user details using userId
            val user = userRepository.getUserStream(userId.value).firstOrNull()
            if (user != null) {
                username.value = user.username
                email.value = user.email
                password.value = user.password
                originalUsername.value = user.username
                originalPassword.value = user.password
            }
        }
    }

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordVisibilityToggle() {
        passwordVisible.value = !passwordVisible.value
    }

    fun onChangeClicked() {
        viewModelScope.launch {
            // Get the user from the database
            val user =
                userRepository.getUserByUsernameAndPassword(originalUsername.value, originalPassword.value)
                    .firstOrNull()

            if (user != null) {
                // update the credentials
                val newUser = user.copy(username = username.value, email = email.value, password = password.value)
                userRepository.updateUser(newUser)
                // Show a success message for the successful update of credentials
                successMessage.value = "Your credentials have been updated successfully!"
            } else {
                // Show error message if an error occurs
                errorMessage.value = "There has been an error. Please try again..."
            }
        }
    }

    @Composable
    fun favoritesResults(): List<Favorite> {
        // Collect the flow as state
        val favoritesList = favoritesRepository.getAllUserFavoritesStream(userId.value).collectAsState(initial = emptyList())
        return favoritesList.value
    }

    fun onRemoveFavorite(favorite: Favorite){
        viewModelScope.launch {
            favoritesRepository.deleteFavorite(favorite)
        }
    }

}
