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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage  // Assuming you're using Coil for image loading


@Composable
fun SearchResultsScreen(
    searchResults: List<Place>,
    onPlaceClick: (Place) -> Unit,
    viewModel: SearchResultsViewModel = viewModel()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Search results for: ${viewModel.query}")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(searchResults) { place -> // Probably it must be changed according to the viewmodel
                PlaceCard(place = place, onPlaceClick = onPlaceClick)
            }
        }
    }
}






@Composable
fun PlaceCard(place: Place, onPlaceClick: (Place) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = place.photos?.firstOrNull()?.photoReference,
                contentDescription = place.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = place.name)
                Text(text = place.types[0])
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