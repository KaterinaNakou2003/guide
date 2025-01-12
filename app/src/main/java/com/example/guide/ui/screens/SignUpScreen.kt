package com.example.guide.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import com.example.guide.data.FakeUsersRepository
import com.example.guide.ui.AppViewModelProvider
import com.example.guide.ui.navigation.NavigationDestination

object SignUpDestination : NavigationDestination {
    override val route: String = "signup"
}

@Composable
fun SignUpScreen(navigateBack: () -> Unit,  // Navigate back to the previous screen
                 navigateToMain: (Int) -> Unit,  // Function to navigate to MainScreen with the id
                 viewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
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
                    text = "Sign Up",
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
            // Username Input
            OutlinedTextField(
                value = viewModel.username.value,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Input
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                visualTransformation = if (viewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (viewModel.passwordVisible.value) Icons.Default.Visibility  else Icons.Default.VisibilityOff
                    IconButton(onClick = { viewModel.onPasswordVisibilityToggle() }) {
                        Icon(imageVector = icon, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.errorMessage.value.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth() // Box width adjusts to text width
                        .padding(top = 8.dp)
                        .background(Color.Red.copy(alpha = 0.1f)) // Red background with low opacity
                        .clip(RoundedCornerShape(3.dp))
                        .padding(8.dp) // Padding for the error text
                ) {
                    Text(
                        text = viewModel.errorMessage.value,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign-Up Button
            Button(
                onClick = {
                    viewModel.onSignUpClicked(navigateToMain)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign-up")
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    val fakeRepository = FakeUsersRepository() // Replace with your mock repo
    val viewModel = SignUpViewModel(fakeRepository)

    // Implementing mocked navigation functions for the preview
    val navigateToMain: (Int) -> Unit = { id ->
        println("Navigate to Main with id: $id") // Simulate navigation (logging for preview)
    }

    // Mocked navigation function for navigating back (no-op for preview)
    val navigateBack: () -> Unit = {
        println("Navigate back to the previous screen")  // Simulate navigation (logging for preview)
    }
    SignUpScreen(navigateBack,  // Navigate back to the previous screen
                navigateToMain,  // Function to navigate to MainScreen with the id
                viewModel)
}