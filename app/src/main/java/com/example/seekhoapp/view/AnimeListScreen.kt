package com.example.seekhoapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.seekhoapp.data.model.Data
import com.example.seekhoapp.data.remote.Resource
import com.example.seekhoapp.ui.theme.Gold
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
            Column(Modifier.padding(8.dp).fillMaxSize()){
                Text("Anime List", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 28.sp, style = MaterialTheme.typography.displaySmall)
                LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                        ) {
                    items(animeList) { anime ->
                        AnimeListItemCard(anime, onAnimeClick)
                    }
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
                Text(text = anime.title, style = MaterialTheme.typography.titleLarge)
                Row {
                    Text(text = "Episodes: ${anime.episodes}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.Movie, contentDescription = "Movie Icon", tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Row {
                    Text(text = "Rating: ${anime.score}")
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Rating Star", tint = Gold, modifier = Modifier.size(20.dp))
                }

            }
        }
    }
}