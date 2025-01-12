package com.example.guide.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.guide.data.FakeUsersRepository
import com.example.guide.ui.AppViewModelProvider
import com.example.guide.ui.navigation.NavigationDestination

object ForgotPasswordDestination : NavigationDestination {
    override val route = "forgotpassword"
}

@Composable
fun ForgotPasswordScreen(navigateBack: () -> Unit,  // Navigate back to the previous screen
                         navigateToMain: (Int) -> Unit,  // Function to navigate to MainScreen with the id
                         viewModel: ForgotPasswordViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Forgot Password",
                    style = TextStyle(
                        color = Color(0xFF800080), // Purple color
                        fontWeight = FontWeight.Bold, // Bold text
                        fontSize = 20.sp // Optional: Change font size
                    ),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = viewModel.username.value,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = viewModel.newPassword.value,
                onValueChange = { viewModel.onNewPasswordChange(it) },
                label = { Text("New Password") },
                modifier = Modifier.fillMaxWidth()
            )
                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Text(
                        text = viewModel.errorMessage.value,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Reset Button
            Button(
                onClick = {
                    viewModel.onResetPassword(navigateToMain)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Password Reset")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewForgotPasswordScreen() {
    val fakeRepository = FakeUsersRepository() //mock repo
    // Implementing mocked navigation functions for the preview
    val navigateToMain: (Int) -> Unit = { id ->
        println("Navigate to Main with id: $id") // Simulate navigation (logging for preview)
    }

    // Mocked navigation function for navigating back (no-op for preview)
    val navigateBack: () -> Unit = {
        println("Navigate back to the previous screen")  // Simulate navigation (logging for preview)
    }

    val viewModel = ForgotPasswordViewModel(fakeRepository)
    ForgotPasswordScreen(navigateBack,  // Navigate back to the previous screen
                        navigateToMain,  // Function to navigate to MainScreen with the id
                        viewModel)
}
