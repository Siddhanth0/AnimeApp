package com.example.seekhoapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.seekhoapp.data.model.AnimeData
import com.example.seekhoapp.data.model.Data
import com.example.seekhoapp.data.remote.Resource
import com.example.seekhoapp.viewmodel.AnimeListViewModel

@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel,
    onAnimeClick: (Int) -> Unit
) {
    val state by viewModel.topAnime.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAnime()
    }

    when (state) {
        is Resource.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            val animeList: List<Data> = (state as? Resource.Success<List<Data>>)?.data ?: emptyList()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(animeList) { anime ->
                    AnimeListItemCard(anime, onAnimeClick)
                }
            }
        }
        is Resource.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${(state as Resource.Error).message}")
            }
        }
    }
}

@Composable
fun AnimeListItemCard(anime: Data, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(anime.mal_id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = anime.images.jpg.image_url,
                contentDescription = anime.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(text = anime.title, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Episodes: ${anime.episodes}")
                Text(text = "Rating: ${anime.score}")
            }
        }
    }
}