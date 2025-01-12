package com.example.guide.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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

object LoginDestination : NavigationDestination {
        override val route = "login"
}

@Composable
fun LoginScreen(
    navigateBack: () -> Unit,
    navigateToMain: (Int) -> Unit,  // Function to navigate to MainScreen with the id
    navigateToForgotPassword: () -> Unit, // Function to navigate to Forgot Password screen
    navigateToSignUp: () -> Unit, // Function to navigate to Sign Up screen
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
                    text = "Login",
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
            // User Input
            OutlinedTextField(
                value = viewModel.username.value,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Username") },
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

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot password Option
            Row(
                modifier = Modifier.fillMaxWidth(), // Makes the row take full width
                horizontalArrangement = Arrangement.End, // Aligns content to the start (left)
                verticalAlignment = Alignment.CenterVertically // Centers vertically in the row
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF800080))) {
                            append("Forgot Password ")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        navigateToForgotPassword()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = {
                    viewModel.onLoginClicked(navigateToMain)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Option
            Text(
                text = buildAnnotatedString {
                    append("Don't have an account? ")

                    withStyle(style = SpanStyle(color = Color(0xFF800080))) {
                        append("Sign Up")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navigateToSignUp()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    
    val fakeRepository = FakeUsersRepository() //mock repo
    val viewModel = LoginViewModel(fakeRepository)

    // Implementing mocked navigation functions for the preview
    val navigateToMain: (Int) -> Unit = { id ->
        println("Navigate to Main with id: $id") // Simulate navigation (logging for preview)
    }

    val navigateToForgotPassword: () -> Unit = {
        println("Navigate to Forgot Password") // Simulate navigation
    }

    val navigateToSignUp: () -> Unit = {
        println("Navigate to Sign Up") // Simulate navigation
    }

    // Mocked navigation function for navigating back (no-op for preview)
    val navigateBack: () -> Unit = {
        println("Navigate back to the previous screen")  // Simulate navigation (logging for preview)
    }

    // LoginScreen Composable
    LoginScreen(
        navigateBack = navigateBack,
        navigateToMain = navigateToMain,
        navigateToForgotPassword = navigateToForgotPassword,
        navigateToSignUp = navigateToSignUp,
        viewModel = viewModel
    )
}
