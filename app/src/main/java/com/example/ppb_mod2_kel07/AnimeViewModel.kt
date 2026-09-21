package com.example.ppb_mod2_kel07

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppb_mod2_kel07.model.Anime
import com.example.ppb_mod2_kel07.network.ApiClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

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
}

