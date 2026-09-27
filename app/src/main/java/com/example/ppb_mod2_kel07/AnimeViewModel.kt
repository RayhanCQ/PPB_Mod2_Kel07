package com.example.ppb_mod2_kel07

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppb_mod2_kel07.model.Anime
import com.example.ppb_mod2_kel07.model.Character
import com.example.ppb_mod2_kel07.network.ApiClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList: StateFlow<List<Character>> = _characterList.asStateFlow()

    private val _isCharacterLoading = MutableStateFlow(false)
    val isCharacterLoading: StateFlow<Boolean> = _isCharacterLoading.asStateFlow()

    private val _characterError = MutableStateFlow<String?>(null)
    val characterError: StateFlow<String?> = _characterError.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchTopAnime()
    }

    fun fetchTopAnime() {
        if (_isLoading.value) return

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = ApiClient.service.getTopAnime()
                _animeList.value = response.data
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _errorMessage.value =
                    "Gagal memuat anime: ${e.localizedMessage ?: "Kesalahan jaringan"}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchTopCharacters(force: Boolean = false) {
        if (_isCharacterLoading.value || (!force && _characterList.value.isNotEmpty())) return

        _isCharacterLoading.value = true
        _characterError.value = null
        viewModelScope.launch {
            try {
                _characterList.value = ApiClient.service.getTopCharacters().data
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _characterList.value = sampleCharacters
                _characterError.value = "Data karakter online sedang tidak tersedia. Menampilkan karakter contoh."
            } finally {
                _isCharacterLoading.value = false
            }
        }
    }

    fun toggleFavorite(animeId: Int) {
        _favoriteIds.value = _favoriteIds.value.let { favorites ->
            if (animeId in favorites) favorites - animeId else favorites + animeId
        }
    }

    private companion object {
        val sampleCharacters = listOf(
            Character(-1, "Monkey D. Luffy", "Kapten Bajak Laut Topi Jerami dari One Piece.", null),
            Character(-2, "Naruto Uzumaki", "Ninja dari Konoha yang bercita-cita menjadi Hokage.", null),
            Character(-3, "Eren Yeager", "Tokoh utama Attack on Titan yang mencari kebebasan.", null),
            Character(-4, "Satoru Gojo", "Penyihir jujutsu terkuat dari Jujutsu Kaisen.", null),
            Character(-5, "Frieren", "Penyihir elf dalam perjalanan memahami manusia.", null)
        )
    }
}

