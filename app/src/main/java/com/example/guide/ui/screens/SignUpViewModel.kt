package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.guide.data.User
import com.example.guide.data.UsersRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UsersRepository) : ViewModel() {
    var username = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

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

    fun onSignUpClicked(navController: NavHostController) {
        viewModelScope.launch {
            val userId = userRepository.getUserIdFromUsername(username.value)
                .firstOrNull()

            if (userId == null) {
                val user =
                    User(username = username.value, email = email.value, password = password.value)
                userRepository.insertUser(user)
                navController.navigate("MainActivity")
            }  else {
                // Show error message if username exists
                errorMessage.value = "This username is in use! Please try another one..."
            }
        }
    }
}