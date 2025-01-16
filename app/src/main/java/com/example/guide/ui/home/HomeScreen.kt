package com.example.guide.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guide.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HomeBody(
            onLoginClick = navigateToLogin,
            onSignInClick = navigateToSignUp,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    onLoginClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to PlaceSpotter",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = onSignInClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign In")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val viewModel = HomeViewModel()

    // Implementing mocked navigation functions for the preview
    val navigateToLogin: () -> Unit = {
        println("Navigate to Login") // Simulate navigation (logging for preview)
    }

    val navigateToSignUp: () -> Unit = {
        println("Navigate to Sign Up") // Simulate navigation
    }

    // LoginScreen Composable
    HomeScreen(
        navigateToSignUp = navigateToSignUp,
        navigateToLogin = navigateToLogin,
        viewModel = viewModel
    )
}