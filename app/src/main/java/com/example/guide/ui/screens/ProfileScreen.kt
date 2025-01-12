package com.example.guide.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guide.data.FakeUsersRepository
import com.example.guide.ui.AppViewModelProvider
import com.example.guide.ui.navigation.NavigationDestination

object ProfileDestination : NavigationDestination {
    override val route = "profile"
}

@Composable
fun ProfileScreen( navigateBack: () -> Unit,
                   navigateToMain: () -> Unit,
                   viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var isAccountInfoExpanded by remember { mutableStateOf(false) }

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
                    text = "Hi, ${viewModel.userId}",
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Account Info Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isAccountInfoExpanded = !isAccountInfoExpanded
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Account Info",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = if (isAccountInfoExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Account Info"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Favorites",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SwipeableCard()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isAccountInfoExpanded) {
                    // Display editable account info when expanded
                    OutlinedTextField(
                        value = viewModel.username.value,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.email.value,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = viewModel.password.value,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = { Text("Password") },
                        visualTransformation = if (viewModel.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon =
                                if (viewModel.passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { viewModel.onPasswordVisibilityToggle() }) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Change Credentials Button
                    Button(
                        onClick = {
                            viewModel.onChangeClicked()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Change Credentials")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            ProfileBottomNavigationBar(
                navigateToMain,
                navigateBack,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

        }


    }
}

@Composable
fun SwipeableCard() {
    Box {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "item.name")
                Text(text = "item.details")
            }
        }
    }
}

// Bottom Navigation Bar Function
@Composable
fun ProfileBottomNavigationBar(
    navigateToMain: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableStateOf(2) }

    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val items = listOf("Back", "Home", "Profile")

        items.forEachIndexed { index, label ->
            NavigationBarItem(
                icon = {
                    when (label) {
                        "Back" -> Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = label
                        )
                        "Home" -> Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = label
                        )
                        "Profile" -> Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = label
                        )
                    }
                },
                label = {
                    Text(
                        text = label,
                        style = TextStyle(
                            color = if (selectedIndex == index) Color.White else Color.Black
                        )
                    )
                },
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    when (label) {
                        "Back" -> navigateBack()
                        "Home" -> navigateToMain()
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val savedStateHandle = remember { SavedStateHandle() }
    savedStateHandle["userId"] = 1 // Set a fake userId for the preview
    val fakeRepository = FakeUsersRepository() //mock repo
    val viewModel = ProfileViewModel(fakeRepository, savedStateHandle)

    // Implementing mocked navigation functions for the preview
    val navigateToMain: () -> Unit = {
        println("Navigate to Main with id") // Simulate navigation (logging for preview)
    }

    // Mocked navigation function for navigating back (no-op for preview)
    val navigateBack: () -> Unit = {
        println("Navigate back to the previous screen")  // Simulate navigation (logging for preview)
    }

    // LoginScreen Composable
    ProfileScreen(
        navigateBack = navigateBack,
        navigateToMain = navigateToMain,
        viewModel = viewModel
    )
}
