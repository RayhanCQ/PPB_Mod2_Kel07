package com.example.ppb_mod2_kel07

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ppb_mod2_kel07.model.Anime

@Composable
fun AnimeListScreen(viewModel: AnimeViewModel, onAnimeClick: (Anime) -> Unit) {
    val animeList by viewModel.animeList.collectAsState()
    val favorites by viewModel.favoriteIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var query by remember { mutableStateOf("") }
    var ascending by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize()) {
        SearchAndSortBar(query, { query = it }, ascending) { ascending = !ascending }
        errorMessage?.let { message ->
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(message, color = MaterialTheme.colorScheme.error)
                Button(onClick = viewModel::fetchTopAnime, enabled = !isLoading) { Text("Coba lagi") }
            }
        }
        when {
            isLoading && animeList.isEmpty() -> LoadingContent(Modifier.fillMaxWidth().weight(1f))
            animeList.isEmpty() -> EmptyContent("Belum ada data anime.", Modifier.fillMaxWidth().weight(1f))
            else -> AnimeList(
                anime = filterAndSortAnime(animeList, query, ascending, favorites),
                favorites = favorites,
                onAnimeClick = onAnimeClick,
                onFavoriteClick = viewModel::toggleFavorite
            )
        }
    }
}

@Composable
fun FavoriteScreen(viewModel: AnimeViewModel, onAnimeClick: (Anime) -> Unit) {
    val animeList by viewModel.animeList.collectAsState()
    val favorites by viewModel.favoriteIds.collectAsState()
    var query by remember { mutableStateOf("") }
    var ascending by remember { mutableStateOf(true) }
    val favoriteAnime = animeList.filter { it.mal_id in favorites }

    Column(Modifier.fillMaxSize()) {
        SearchAndSortBar(query, { query = it }, ascending) { ascending = !ascending }
        if (favoriteAnime.isEmpty()) EmptyContent("Belum ada anime favorit.", Modifier.fillMaxWidth().weight(1f)) else AnimeList(
            anime = filterAndSortAnime(favoriteAnime, query, ascending, favorites),
            favorites = favorites,
            onAnimeClick = onAnimeClick,
            onFavoriteClick = viewModel::toggleFavorite
        )
    }
}

@Composable
private fun androidx.compose.foundation.layout.ColumnScope.AnimeList(
    anime: List<Anime>, favorites: Set<Int>, onAnimeClick: (Anime) -> Unit, onFavoriteClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().weight(1f),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(anime, key = { it.mal_id }) {
            AnimeCard(it, it.mal_id in favorites, { onAnimeClick(it) }, onFavoriteClick)
        }
    }
}

@Composable
fun AnimeCard(anime: Anime, isFavorite: Boolean, onClick: () -> Unit, onFavoriteClick: (Int) -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(
                model = anime.images?.jpg?.image_url,
                contentDescription = "Poster ${anime.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(80.dp).height(115.dp)
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(anime.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text("Type: ${anime.type ?: "-"}")
                Text("Episodes: ${anime.episodes ?: "-"}")
                Text("Score: ${anime.score ?: "N/A"}")
            }
            IconButton(onClick = { onFavoriteClick(anime.mal_id) }) {
                Icon(
                    if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (isFavorite) "Hapus dari favorit" else "Tambah ke favorit",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SearchAndSortBar(query: String, onQueryChange: (String) -> Unit, ascending: Boolean, onSortClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            label = { Text("Cari") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
        )
        TextButton(onClick = onSortClick) { Text(if (ascending) "A-Z" else "Z-A") }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier) = Box(modifier, contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
}

@Composable
fun EmptyContent(text: String, modifier: Modifier = Modifier.fillMaxSize()) = Box(modifier, contentAlignment = Alignment.Center) {
    Text(text)
}

fun filterAndSortAnime(anime: List<Anime>, query: String, ascending: Boolean, favorites: Set<Int>): List<Anime> {
    val matched = anime.filter { it.title.contains(query.trim(), ignoreCase = true) }
    val alphabetic = if (ascending) matched.sortedBy { it.title.lowercase() } else matched.sortedByDescending { it.title.lowercase() }
    return alphabetic.sortedByDescending { it.mal_id in favorites }
}
