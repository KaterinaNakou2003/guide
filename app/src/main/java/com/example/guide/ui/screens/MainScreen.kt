package com.example.guide.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.guide.R
import com.example.guide.ui.AppViewModelProvider
import com.example.guide.ui.navigation.NavigationDestination

object MainDestination : NavigationDestination {
    override val route = "main"
}

@Composable
fun MainScreen(navigateBack: () -> Unit,
               navigateToProfile: () -> Unit,
               navigateToSearch: () -> Unit,
               navigateToResults: () -> Unit,
               viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Search Bar Section
            Column(
                modifier = Modifier.weight(1f) // Fill available space
            ) {
                SearchBar(
                    query = viewModel.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                    navigateBack,
                    navigateToResults = navigateToResults
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))
                // Buttons Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.onButtonClick("monuments") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Monuments")
                        }

                        Button(
                            onClick = { viewModel.onButtonClick("museums") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Museums")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.onButtonClick("cafes") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cafes")
                        }

                        Button(
                            onClick = { viewModel.onButtonClick("restaurants") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Restaurants")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.onButtonClick("bars") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Bars")
                        }

                        Button(
                            onClick = { viewModel.onButtonClick("bakeries") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Bakeries")
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
        }
        BottomNavigationBar(
            navigateToProfile,
            navigateToSearch,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    navigateBack: () -> Unit,
    navigateToResults: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween, // Adjust spacing as needed
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = "Search here for your destination...",
                    style = TextStyle(color = MaterialTheme.colorScheme.primary)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .weight(1f),
            singleLine = true
        )
        IconButton(
            onClick = {
                /* navigate to next page everything else is handled from the NavGraph
                 * or the SearchResultsViewModel
                */

                navigateToResults()
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )

        }
    }
}


// Bottom Navigation Bar Function
@Composable
fun BottomNavigationBar(
    navigateToProfile: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableStateOf(1) }

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
                        "Profile" -> navigateToProfile()
                    }
                }
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val navigateToProfile: () -> Unit = {
        println("Navigate to Profile") // Simulate navigation (logging for preview)
    }

    val navigateToSearch: () -> Unit = {
        println("Navigate to Search") // Simulate navigation (logging for preview)
    }
    val navigateToResults: () -> Unit = {
        println("Navigate to Search Results") // Simulate navigation (logging for preview)
    }

    // Mocked navigation function for navigating back (no-op for preview)
    val navigateBack: () -> Unit = {
        println("Navigate back to the previous screen")  // Simulate navigation (logging for preview)
    }
    val viewModel = MainViewModel()

    MainScreen(navigateBack,
            navigateToProfile,
            navigateToSearch,
            navigateToResults,
            viewModel)
}