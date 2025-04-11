package com.example.seekhoapp.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.seekhoapp.data.model.Data
import com.example.seekhoapp.data.remote.Resource
import com.example.seekhoapp.ui.theme.Gold
import com.example.seekhoapp.viewmodel.AnimeListViewModel


@Composable
fun AnimeDetailScreen(
    viewModel: AnimeListViewModel,
    animeId: Int
) {
    val state by viewModel.animeDetail.collectAsState()

    LaunchedEffect(animeId) {
        viewModel.getAnimeDetail(animeId)
    }

    when (state) {
        is Resource.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            state.data?.let { AnimeDetailContent(anime = it) }
        }
        is Resource.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading anime details.")
            }
        }
    }
}

@Composable
fun AnimeDetailContent(anime: Data) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (!anime.trailer?.youtube_id.isNullOrEmpty()) {
            val context = LocalContext.current
            val trailerUrl = "https://www.youtube.com/watch?v=${anime.trailer.youtube_id}"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                        context.startActivity(intent)
                    }
            ) {
                // Blurred background image
                AsyncImage(
                    model = anime.images.jpg.large_image_url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            alpha = 0.9f
                        }
                        .blur(16.dp)
                )

                // Dark overlay
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )

                // Centered Play Button
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Trailer",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp)
                )
            }
        } else {
            AsyncImage(
                model = anime.images.jpg.large_image_url,
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = anime.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Synopsis:", fontWeight = FontWeight.Bold)
        Text(text = anime.synopsis)
        Spacer(modifier = Modifier.height(8.dp))

        if (!anime.genres.isNullOrEmpty()) {
            Text(text = "Genres:", fontWeight = FontWeight.Bold)
            Text(text = anime.genres.joinToString { it.name })
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(text = "Episodes:", fontWeight = FontWeight.Bold)
        Row {
            Text(text = anime.episodes.toString())
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Movie,
                contentDescription = "Movie Icon",
                tint = Color.Black,
                modifier = Modifier.size(21.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))


        Text(text = "Rating:", fontWeight = FontWeight.Bold)
        Row {
            Text(text = anime.score.toString())
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating Star",
                tint = Gold,
                modifier = Modifier.size(21.dp)
            )
        }
    }
}