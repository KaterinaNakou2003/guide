package com.example.guide.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.guide.data.AppDataContainer
import com.example.guide.ui.AppViewModelProvider
import com.example.guide.ui.DetailsViewModelFactory
import com.example.guide.ui.SearchResultsViewModelFactory
import com.example.guide.ui.ProfileViewModelFactory

import com.example.guide.ui.screens.ForgotPasswordDestination
import com.example.guide.ui.screens.ForgotPasswordScreen

import com.example.guide.ui.home.HomeDestination
import com.example.guide.ui.home.HomeScreen
import com.example.guide.ui.home.HomeViewModel
import com.example.guide.ui.screens.DetailsDestination
import com.example.guide.ui.screens.DetailsScreen
import com.example.guide.ui.screens.DetailsViewModel

import com.example.guide.ui.screens.LoginDestination
import com.example.guide.ui.screens.LoginScreen
import com.example.guide.ui.screens.LoginViewModel

import com.example.guide.ui.screens.MainDestination
import com.example.guide.ui.screens.MainScreen
import com.example.guide.ui.screens.MainViewModel

import com.example.guide.ui.screens.ProfileDestination
import com.example.guide.ui.screens.ProfileScreen
import com.example.guide.ui.screens.ProfileViewModel

import com.example.guide.ui.screens.SearchResultsDestination
import com.example.guide.ui.screens.SearchResultsScreen
import com.example.guide.ui.screens.SearchResultsViewModel
import com.example.guide.ui.screens.SharedPlaceViewModel

import com.example.guide.ui.screens.SignUpDestination
import com.example.guide.ui.screens.SignUpScreen
import com.example.guide.ui.screens.SignUpViewModel


/**
 * Provides Navigation graph for the application.
 */
@SuppressLint("NewApi")
@Composable
fun GuideNavHost(
    navController: NavHostController
) {
    val loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val signUpViewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val mainViewModel : MainViewModel = viewModel()
    // Get instance of UserRepository for the profile view model
    val container = AppDataContainer(navController.context)
    val userRepository= container.usersRepository
    val favoritesRepository = container.favoritesRepository
    // Create the shared viewmodel to pass the place to the details screen
    val sharedViewModel: SharedPlaceViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        // Home Screen
        composable(route = HomeDestination.route) {
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

        // Main Screen
        composable(route = "${MainDestination.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            MainScreen(
                navigateBack = { navController.navigateUp() },
                navigateToProfile = { navController.navigate("profile/$userId") },
                navigateToSearch = { },
                // Navigation connection with results page. The search query is also needed.
                navigateToResults = {navController.navigate("${SearchResultsDestination.route}/$userId/${mainViewModel.searchQuery}")} ,
                viewModel = mainViewModel
            )
        }

        // Search Results Screen
        composable(route = "${SearchResultsDestination.route}/{userId}/{query}",
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("query") { type = NavType.StringType })
        ){backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val query = backStackEntry.arguments?.getString("query")
            // Create the viewmodel later as the query needs to be passed as the parameter
            val searchResultsViewModel: SearchResultsViewModel = viewModel(factory = SearchResultsViewModelFactory(query))
            SearchResultsScreen(
                navigateBack = { navController.navigateUp() },
                navigateToProfile = { navController.navigate("profile/$userId") },
                navigateToSearch = { navController.navigate("search/$userId") },
                onPlaceClick = {place ->
                    sharedViewModel.setSelectedPlace(place)
                    navController.navigate("${DetailsDestination.route}/$userId")},
                viewModel = searchResultsViewModel
            )
        }

        // Profile Screen
        composable(route = "${ProfileDestination.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(userId, userRepository, favoritesRepository)
            )
            ProfileScreen(
                navigateBack = { navController.navigateUp() },
                navigateToMain = {navController.navigate("${MainDestination.route}/$userId") },
                viewModel = profileViewModel
            )
        }

        // Details Screen
        composable(route = "${DetailsDestination.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val viewModel : DetailsViewModel = viewModel(factory = DetailsViewModelFactory(userId, sharedViewModel, favoritesRepository))
            DetailsScreen(
                navigateBack = { navController.navigateUp() },
                navigateToProfile = { navController.navigate("profile/$userId") },
                navigateToSearch = { navController.navigate("search/$userId") },
                viewModel = viewModel
            )
        }

    }
}
