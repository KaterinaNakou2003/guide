package com.example.guide.ui


import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.guide.GuideApplication
import com.example.guide.ui.home.HomeViewModel
import com.example.guide.ui.screens.ForgotPasswordViewModel
import com.example.guide.ui.screens.LoginViewModel
import com.example.guide.ui.screens.MainViewModel
import com.example.guide.ui.screens.SignUpViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for ForgotPasswordViewModel
        initializer {
            ForgotPasswordViewModel(GuideApplication().container.usersRepository)
        }

        // Initializer for LoginViewModel
        initializer {
            LoginViewModel(GuideApplication().container.usersRepository)
        }
        // Initializer for SignUpViewModel
        initializer {
            SignUpViewModel(GuideApplication().container.usersRepository)
        }

        // Initializer for MainViewModel
        initializer {
           MainViewModel()
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel()
        }
    }
}
