package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.guide.data.UsersRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UsersRepository) : ViewModel() {
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onPasswordVisibilityToggle() {
        passwordVisible.value = !passwordVisible.value
    }

    fun onLoginClicked(navigateToMain: (Int) -> Unit) {
        viewModelScope.launch {
            // Get the user from the database
            val user = userRepository.getUserByUsernameAndPassword(username.value, password.value)
                .firstOrNull()

            if (user != null && user.password == password.value) {
                // User exists and password matches
                val userId = userRepository.getUserIdFromUsername(username.value)
                    .firstOrNull()
                if (userId != null)
                    navigateToMain(userId)
                else
                    // Show error message if credentials are invalid
                    errorMessage.value = "There has been an error. Please try again..."
            } else {
                // Show error message if credentials are invalid
                errorMessage.value = "Invalid username or password"
            }
        }
    }
}
