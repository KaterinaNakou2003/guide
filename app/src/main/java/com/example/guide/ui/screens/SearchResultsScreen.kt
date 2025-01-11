package com.example.guide.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.guide.network.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage  // Assuming you're using Coil for image loading
import com.example.guide.ui.navigation.NavigationDestination


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
                navigateToProfile,
                navigateToSearch,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Search results for: ${viewModel.query.value}")
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(viewModel.searchResults.value) { place -> // Probably it must be changed according to the viewmodel
                    PlaceCard(place = place, onPlaceClick = onPlaceClick)
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
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = place.photos?.firstOrNull()?.photoReference,
                contentDescription = place.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = place.name)
                Text(text = "Rating: ${place.rating}")
                Text(text = place.formattedAddress)
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { onPlaceClick(place) }) {
                Text("Details")
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
