package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guide.data.User
import com.example.guide.data.UsersRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userID: Int,
    private val userRepository: UsersRepository) : ViewModel() {

    val userId = mutableStateOf(userID)
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var email = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    val originalUsername = mutableStateOf("")
    val originalPassword = mutableStateOf("")

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
            // update the credentials
            val newUser =
                User(username = username.value, email = email.value, password = password.value)
            if (user != null) {
                userRepository.updateUser(newUser)


            } else {
                // Show error message if an error occurs
                errorMessage.value = "There has been an error. Please try again..."
            }
        }
    }
}
