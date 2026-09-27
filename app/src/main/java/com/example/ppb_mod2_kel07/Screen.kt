package com.example.ppb_mod2_kel07

sealed class Screen(val route: String, val title: String) {
    data object Anime : Screen("anime", "Anime")
    data object Character : Screen("character", "Karakter")
    data object Favorite : Screen("favorite", "Favorit")
    data object About : Screen("about", "Tentang")
    data object Detail : Screen("detail/{animeId}", "Detail") {
        fun route(animeId: Int) = "detail/$animeId"
    }
}
