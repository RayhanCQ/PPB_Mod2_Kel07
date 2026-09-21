package com.example.ppb_mod2_kel07.network

import com.example.ppb_mod2_kel07.model.AnimeListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("top/anime")
    suspend fun getTopAnime(): AnimeListResponse
}

