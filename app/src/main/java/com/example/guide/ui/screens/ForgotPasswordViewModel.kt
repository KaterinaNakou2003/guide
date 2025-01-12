package com.example.guide.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.example.guide.data.UsersRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val userRepository: UsersRepository): ViewModel() {
    var username = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var errorMessage = mutableStateOf("")

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onNewPasswordChange(newPass: String) {
        newPassword.value = newPass
    }

    fun onResetPassword(navigateToMain: (Int) -> Unit) {
        viewModelScope.launch {
            val userId = userRepository.getUserIdFromUsername(username.value)
                .firstOrNull()

            if (userId != null) {
                val user = userRepository.getUserStream(userId)
                    .firstOrNull()
                if (user != null) {
                    val updatedUser = user.copy(password = newPassword.value)
                    userRepository.updateUser(updatedUser)
                    navigateToMain(userId)
                }else {
                    errorMessage.value = "There has been an error. Please try again..."
                }
            } else {
                errorMessage.value = "Invalid username"
            }
        }
    }
}
