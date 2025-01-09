package com.example.guide.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.guide.ui.screens.ForgotPasswordDestination
import com.example.guide.ui.screens.ForgotPasswordScreen

import com.example.guide.ui.home.HomeDestination
import com.example.guide.ui.home.HomeScreen
import com.example.guide.ui.home.HomeViewModel

import com.example.guide.ui.screens.LoginDestination
import com.example.guide.ui.screens.LoginScreen
import com.example.guide.ui.screens.LoginViewModel

import com.example.guide.ui.screens.MainDestination

import com.example.guide.ui.screens.SignUpDestination
import com.example.guide.ui.screens.SignUpScreen
import com.example.guide.ui.screens.SignUpViewModel

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun GuideNavHost(
    navController: NavHostController
) {
    val loginViewModel: LoginViewModel = viewModel()
    val signUpViewModel: SignUpViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        // Home Screen
        composable(route = LoginDestination.route) {
            HomeScreen(
                navigateToLogin = { navController.navigate(LoginDestination.route) },
                navigateToSignUp = { navController.navigate(SignUpDestination.route) },
                viewModel = homeViewModel
            )
        }
        // Login Screen
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateBack = { navController.navigateUp() },
                navigateToMain = { id -> navController.navigate("${MainDestination.route}/$id") },
                navigateToForgotPassword = { navController.navigate(ForgotPasswordDestination.route) },
                navigateToSignUp = { navController.navigate(SignUpDestination.route) },
                viewModel = loginViewModel
            )
        }
        // Forgot Password Screen
        composable(route = ForgotPasswordDestination.route) {
            ForgotPasswordScreen(
                navigateBack = { navController.navigateUp() },
                navigateToMain = { id -> navController.navigate("${MainDestination.route}/$id") }
            )
        }
        // Sign Up Screen
        composable(route = SignUpDestination.route) {
            SignUpScreen(
                navigateBack = { navController.navigateUp() },
                navigateToMain = { id -> navController.navigate("${MainDestination.route}/$id") },
                viewModel = signUpViewModel
            )
        }

    }
}