package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.guide.data.UsersRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val userRepository: UsersRepository) : ViewModel() {
    var username = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onNewPasswordChange(newPass: String) {
        newPassword.value = newPass
    }

    fun onResetPassword(navController: NavHostController) {
        viewModelScope.launch {
            val userId = userRepository.getUserIdFromUsername(username.value)
                .firstOrNull()

            if (userId != null) {
                val user = userRepository.getUserStream(userId)
                    .firstOrNull()
                if (user != null) {
                    userRepository.updateUser(user)
                    navController.navigate("LoginActivity") // Navigate to login screen
                }else {
                    errorMessage.value = "Invalid username"
                }
            } else {
                errorMessage.value = "Invalid username"
            }
        }
    }
}
