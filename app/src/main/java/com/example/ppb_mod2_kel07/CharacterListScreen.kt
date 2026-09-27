package com.example.ppb_mod2_kel07

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ppb_mod2_kel07.model.Character

@Composable
fun CharacterListScreen(viewModel: AnimeViewModel) {
    val characters by viewModel.characterList.collectAsState()
    val isLoading by viewModel.isCharacterLoading.collectAsState()
    val errorMessage by viewModel.characterError.collectAsState()
    var query by remember { mutableStateOf("") }
    var ascending by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) { viewModel.fetchTopCharacters() }
    val result = characters.filter { it.name.contains(query.trim(), ignoreCase = true) }.let {
        if (ascending) it.sortedBy { character -> character.name.lowercase() }
        else it.sortedByDescending { character -> character.name.lowercase() }
    }

    Column(Modifier.fillMaxSize()) {
        SearchAndSortBar(query, { query = it }, ascending) { ascending = !ascending }
        when {
            isLoading && characters.isEmpty() -> EmptyContent(
                "Memuat karakter...", Modifier.fillMaxWidth().weight(1f)
            )
            characters.isEmpty() -> Column(
                Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(errorMessage ?: "Data karakter tidak tersedia.")
                Button(onClick = { viewModel.fetchTopCharacters(force = true) }) { Text("Coba lagi") }
            }
            else -> {
                errorMessage?.let { Text(it, Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(result, key = { it.mal_id }) { CharacterCard(it) }
                }
            }
        }
    }
}

@Composable
private fun CharacterCard(character: Character) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (character.images?.jpg?.image_url != null) {
                AsyncImage(
                    model = character.images.jpg.image_url,
                    contentDescription = "Foto ${character.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(80.dp).height(100.dp)
                )
            } else Box(
                modifier = Modifier.width(80.dp).height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Person, contentDescription = null)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(character.name, style = MaterialTheme.typography.titleMedium)
                Text(character.about ?: "Deskripsi tidak tersedia.", maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
