package com.example.ppb_mod2_kel07.model

data class AnimeResponse(
    val data: Anime
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val type: String?,
    val episodes: Int?,
    val score: Double?,
    val images: Images?,
    val synopsis: String?,
    val status: String?,
    val aired: Aired?
)

data class Images(
    val jpg: Jpg?
)

data class Jpg(
    val image_url: String?
)

data class Aired(
    val string: String?
)

