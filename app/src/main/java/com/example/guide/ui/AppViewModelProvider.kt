package com.example.guide.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.guide.GuideApplication
import com.example.guide.ui.home.HomeViewModel
import com.example.guide.ui.screens.ForgotPasswordViewModel
import com.example.guide.ui.screens.LoginViewModel
import com.example.guide.ui.screens.MainViewModel
import com.example.guide.ui.screens.SignUpViewModel
import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guide.ui.screens.ProfileViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Guide app
 * except for SearchResultsViewModel.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for ForgotPasswordViewModel
        initializer {
            ForgotPasswordViewModel(guideApplication().container.usersRepository,this.createSavedStateHandle())
        }

        // Initializer for LoginViewModel
        initializer {
            LoginViewModel(guideApplication().container.usersRepository,this.createSavedStateHandle())
        }
        // Initializer for SignUpViewModel
        initializer {
            SignUpViewModel(guideApplication().container.usersRepository,this.createSavedStateHandle())
        }

        // Initializer for MainViewModel
        initializer {
           MainViewModel()
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel()
        }

        // Initializer for ProfileViewModel
        initializer {
            ProfileViewModel(guideApplication().container.usersRepository,this.createSavedStateHandle())
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [GuideApplication].
 */
fun CreationExtras.guideApplication(): GuideApplication {
    val application = this[AndroidViewModelFactory.APPLICATION_KEY]
        ?: throw IllegalStateException("Application is not available in CreationExtras")
    return application as GuideApplication
}

