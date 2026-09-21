package com.example.ppb_mod2_kel07.model

data class AnimeListResponse(
    val data: List<Anime>
)

data class CharacterListResponse(
    val data: List<Character>
)

data class Character(
    val mal_id: Int,
    val name: String,
    val about: String?,
    val images: Images?
)

