package com.example.ppb_mod2_kel07

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ppb_mod2_kel07.model.Anime

@Composable
fun AnimeDetailScreen(anime: Anime?, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    if (anime == null) {
        EmptyContent("Anime tidak ditemukan.")
        return
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = anime.images?.jpg?.image_url,
            contentDescription = "Poster ${anime.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(320.dp).padding(horizontal = 56.dp)
        )
        Text(anime.title, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
        OutlinedButton(onClick = onFavoriteClick) {
            Icon(if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder, contentDescription = null)
            Text(if (isFavorite) " Favorit" else " Tambah favorit")
        }
        DetailRow("Tipe", anime.type)
        DetailRow("Episode", anime.episodes?.toString())
        DetailRow("Skor", anime.score?.toString())
        DetailRow("Status", anime.status)
        DetailRow("Tayang", anime.aired?.string)
        Text("Sinopsis", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())
        Text(anime.synopsis ?: "Sinopsis tidak tersedia.", modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun DetailRow(label: String, value: String?) {
    Text("$label: ${value ?: "-"}", modifier = Modifier.fillMaxWidth())
}
