package com.example.seekhoapp.view

import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.seekhoapp.data.model.Data
import com.example.seekhoapp.data.remote.Resource
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
        if (anime.trailer.embed_url.isNotEmpty()) {
            AndroidView(
                factory = { context ->
                    val youtubeId = anime.trailer.youtube_id
                    val constructedEmbedUrl = youtubeId.let { "https://www.youtube.com/embed/$youtubeId?autoplay=1" }
                    Log.d("ANIMEDETAIL", "Loading embed URL: $constructedEmbedUrl, YouTube ID: $youtubeId")

                    WebView(context).apply {
                        Log.d("ANIMEDETAIL", constructedEmbedUrl)
                        settings.javaScriptEnabled = true
                        settings.pluginState = WebSettings.PluginState.ON
                        settings.domStorageEnabled = true
                        loadUrl(constructedEmbedUrl)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
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

        if (anime.genres.isNotEmpty()) {
            Text(text = "Genres:", fontWeight = FontWeight.Bold)
            Text(text = anime.genres.joinToString { it.name })
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(text = "Episodes:", fontWeight = FontWeight.Bold)
        Text(text = anime.episodes.toString())
        Spacer(modifier = Modifier.height(8.dp))


        Text(text = "Rating:", fontWeight = FontWeight.Bold)
        Text(text = anime.score.toString())

    }
}