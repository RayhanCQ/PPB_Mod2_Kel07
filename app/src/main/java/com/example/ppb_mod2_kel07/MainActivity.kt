package com.example.ppb_mod2_kel07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                AnimeApp()
            }
        }
    }
}

@Composable
fun AnimeApp() {
    val navController = rememberNavController()
    val viewModel: AnimeViewModel = viewModel()

    val screens = listOf(
        Screen.Anime,
        Screen.Character,
        Screen.Favorite,
        Screen.About
    )

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(
                                    navController.graph
                                        .findStartDestination()
                                        .id
                                ) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = when (screen) {
                                    Screen.Anime -> Icons.Default.Movie
                                    Screen.Character -> Icons.Default.People
                                    Screen.Favorite -> Icons.Default.Star
                                    Screen.About -> Icons.Default.Info
                                    Screen.Detail -> Icons.Default.Movie
                                },
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(screen.title)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Anime.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Anime.route) {
                AnimeListScreen(viewModel) { anime -> navController.navigate(Screen.Detail.route(anime.mal_id)) }
            }

            composable(Screen.Character.route) {
                CharacterListScreen(viewModel)
            }

            composable(Screen.Favorite.route) {
                FavoriteScreen(viewModel) { anime -> navController.navigate(Screen.Detail.route(anime.mal_id)) }
            }

            composable(Screen.About.route) {
                AboutScreen()
            }

            composable(Screen.Detail.route) { backStackEntry ->
                val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull()
                val anime by viewModel.animeList.collectAsState()
                val favorites by viewModel.favoriteIds.collectAsState()
                val selectedAnime = anime.firstOrNull { it.mal_id == animeId }
                AnimeDetailScreen(selectedAnime, animeId in favorites) { animeId?.let(viewModel::toggleFavorite) }
            }
        }
    }
}
