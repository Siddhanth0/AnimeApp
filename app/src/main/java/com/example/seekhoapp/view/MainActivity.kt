package com.example.seekhoapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.seekhoapp.ui.theme.SeekhoAppTheme
import com.example.seekhoapp.viewmodel.AnimeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeekhoAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "anime_list"
                ) {
                    composable("anime_list") {
                        val viewModel: AnimeListViewModel = hiltViewModel()
                        AnimeListScreen(
                            viewModel = viewModel,
                            onAnimeClick = { animeId ->
                                navController.navigate("anime_detail/$animeId")
                            }
                        )
                    }

                    composable(
                        "anime_detail/{animeId}",
                        arguments = listOf(navArgument("animeId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val viewModel: AnimeListViewModel = hiltViewModel()
                        val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
                        AnimeDetailScreen(
                            viewModel = viewModel,
                            animeId = animeId
                        )
                    }
                }
            }
        }
    }
}