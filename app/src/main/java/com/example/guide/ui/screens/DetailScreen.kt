package com.example.guide.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.guide.R
import com.example.guide.network.Place
import com.example.guide.network.Review
import com.example.guide.ui.navigation.NavigationDestination
import java.util.Locale
import com.example.guide.AppConfig


object DetailsDestination : NavigationDestination {
    override val route = "details"
}

@Composable
fun DetailsScreen(
    navigateBack: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: DetailsViewModel = viewModel(),
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
        val place = viewModel.place.collectAsState().value!!
        // Get the reference for the place's photo
        val photoReference = place.photos?.firstOrNull()?.photoReference
        // Insert the API Key
        val apiKey = AppConfig.API_KEY
        // Build the URL for the photo
        val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=$apiKey"
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Place Name
            Text(
                text = place.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Place Photo
            AsyncImage(
                model = photoUrl,
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Details Section
            DetailsSection(place)

            Spacer(modifier = Modifier.height(16.dp))

            // Reviews Section (LazyColumn)
            ReviewsSection(place) // Assuming you have a ReviewsSection composable

            Spacer(modifier = Modifier.height(16.dp))

            // Favorite Button
            FavoriteButton(
                isFavorite = viewModel.isPlaceFavorite.collectAsState().value,
                onClick = { viewModel.onFavoriteButtonClick(place) }
            )
        }
    }
}

// Details Section Composable
@Composable
fun DetailsSection(place: Place) {
    Column {
        Text(text = "Details", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
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

// Reviews Section Composable (Placeholder)
@Composable
fun ReviewsSection(place: Place) {
    Column {
        Text(text = "Reviews", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        if (place.reviews.isNullOrEmpty()) {
            Text(
                text = "No reviews available for ${place.name}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )

        } else {
            LazyColumn() {
                items(place.reviews) {
                    ReviewCard(it)
                }
            }

        }
    }
}


@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.authorName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = review.relativeTimeDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(review.rating.toInt()) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = Color.Yellow
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = review.text)
        }
    }
}

// Favorite Button Composable
@Composable
fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = if (isFavorite) "Remove from favorites" else "Add to favorites")
    }
}
