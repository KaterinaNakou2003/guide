package com.example.guide.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.guide.R
import com.example.guide.data.FakeFavoritesRepository
import com.example.guide.data.FakeUsersRepository
import com.example.guide.data.Favorite
import com.example.guide.ui.navigation.NavigationDestination
import java.util.Locale

object ProfileDestination : NavigationDestination {
    override val route = "profile"
}

@Composable
fun ProfileScreen( navigateBack: () -> Unit,
                   navigateToMain: () -> Unit,
                   viewModel: ProfileViewModel = viewModel()
) {
    var isAccountInfoExpanded by remember { mutableStateOf(false) }
    var isFavoritesExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Hello, ${viewModel.username.value}!",
                        style = TextStyle(
                            color = Color(0xFF800080),
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
                // Account Info Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { isAccountInfoExpanded = !isAccountInfoExpanded },
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

                if (isAccountInfoExpanded) {
                    Column {
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
                                val icon = if (viewModel.passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
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

                        Button(
                            onClick = {
                                viewModel.onChangeClicked()
                                isAccountInfoExpanded = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Change Credentials")
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Favorites Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { isFavoritesExpanded = !isFavoritesExpanded },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = if (isFavoritesExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Favorites"
                    )
                }

                if (isFavoritesExpanded) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp) // Restrict height to enable scrolling
                        .padding(8.dp)
                    ) {
                        HorizontalPlaceCards(viewModel)
                    }
                }
            }

            // Navigation Bar at the Bottom
            ProfileBottomNavigationBar(
                navigateToMain,
                navigateBack,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Align to the bottom of the screen
                    .fillMaxWidth()
            )
    }
}

@Composable
fun HorizontalPlaceCards(viewModel: ProfileViewModel = viewModel()) {
    val favoritesResults = viewModel.favoritesResults()
    var currentPlaceIndex by remember { mutableStateOf(0) }

    if (favoritesResults.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No favorite places available",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume() // Consume the gesture event
                        if (dragAmount > 0 && currentPlaceIndex > 0) {
                            // Swipe Right -> Previous
                            currentPlaceIndex--
                        } else if (dragAmount < 0 && currentPlaceIndex < favoritesResults.size - 1) {
                            // Swipe Left -> Next
                            currentPlaceIndex++
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            FavoritesCard(
                favoritePlace = favoritesResults[currentPlaceIndex],
                viewModel = viewModel
            )
        }
    }
}


@Composable
fun FavoritesCard(
    favoritePlace: Favorite,
    viewModel: ProfileViewModel
){

    val apiKey = "AIzaSyD5brYYjEkNJOMyM18smT_grgDwuCddHQQ"
    val photoRefernce = favoritePlace.placePhotoReference
    // Build the URL for the photo
    val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoRefernce&key=$apiKey"

    Card{
        Row(verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = favoritePlace.placeName,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = favoritePlace.placeName,
                    fontWeight = FontWeight.Bold
                )
                // Ensure that types have no underscores and capitalize the first letter
                Text(
                    text = favoritePlace.placeType
                        ?.replace("_", " ")
                        ?.replaceFirstChar { char ->
                            if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                        }
                        ?: "No type available"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Rating: ${favoritePlace.placeRating}")
                Text(text = "Address: ${favoritePlace.placeAddress}")
                Button(
                    onClick = {
                        viewModel.onRemoveFavorite(favoritePlace)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Remove from favorites"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text ="Remove")
                }
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
    val fakeUserRepository = FakeUsersRepository() //mock repo
    val fakeFavoriteRepository = FakeFavoritesRepository()
    val userID = 5
    val viewModel = ProfileViewModel(userID, fakeUserRepository, fakeFavoriteRepository)

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
