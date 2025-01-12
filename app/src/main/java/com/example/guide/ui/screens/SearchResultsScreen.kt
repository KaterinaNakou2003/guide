package com.example.guide.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guide.network.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.guide.R
import com.example.guide.ui.navigation.NavigationDestination
import kotlin.text.isLowerCase
import kotlin.text.replaceFirstChar
import kotlin.text.titlecase


object SearchResultsDestination : NavigationDestination {
    override val route = "search_results"
}



@Composable
fun SearchResultsScreen(
    navigateBack: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
    onPlaceClick: (Place) -> Unit,
    viewModel: SearchResultsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navigateBack,
                navigateToSearch,
                navigateToProfile,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        when (viewModel.placesApiUiState) {
            is PlacesApiUiState.Loading ->{
                Column(modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)) {
                    Text("Search results for: ${viewModel.query.value}")
                    Spacer(modifier = Modifier.height(16.dp))
                    LoadingScreen(modifier.fillMaxSize().padding(innerPadding))
                }
            }
            is PlacesApiUiState.Error -> {
                Column(modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)) {
                Text("Search results for: ${viewModel.query.value}")
                Spacer(modifier = Modifier.height(16.dp))
                ErrorScreen(modifier.fillMaxSize().padding(innerPadding))
            }
                }
            is PlacesApiUiState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Text("Search results for: ${viewModel.query.value}")
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn {
                        items(viewModel.searchResults.value) { place ->
                            PlaceCard(place = place, onPlaceClick = onPlaceClick)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PlaceCard(place: Place,
              onPlaceClick: (Place) -> Unit,
              modifier: Modifier = Modifier)
{
    // Get the reference for the place's photo
    val photoReference = place.photos?.firstOrNull()?.photoReference
    // Insert the API Key
    val apiKey = "AIzaSyD5brYYjEkNJOMyM18smT_grgDwuCddHQQ"
    // Build the URL for the photo
    val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=$apiKey"
    Card(
        onClick = { onPlaceClick(place) },
        modifier = Modifier.fillMaxWidth().padding(8.dp))
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = place.name,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = place.name,
                    fontWeight = FontWeight.Bold
                )
                // Ensure that types have no underscores and capitalize the first letter
                Text(text = place.types[0]
                    .replace("_"," ")
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                         Locale.getDefault()) else it.toString()
                    })
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Rating: ${place.rating}")
                Text(text = "Address: ${place.formattedAddress}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultsScreenPreview() {
    val navigateToProfile: () -> Unit = {
        println("Navigate to Profile") // Simulate navigation (logging for preview)
    }
    val navigateToSearch: () -> Unit = {
        println("Navigate to Search") // Simulate navigation (logging for preview)
    }
    val navigateBack: () -> Unit = {
        println("Navigate back to the previous screen")  // Simulate navigation (logging for preview)
    }
    val viewModel = SearchResultsViewModel(query = "example query")
    SearchResultsScreen(navigateBack,
        navigateToProfile,
        navigateToSearch,
        onPlaceClick = {},
        viewModel)

}
