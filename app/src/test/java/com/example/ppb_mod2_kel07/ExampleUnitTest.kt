package com.example.ppb_mod2_kel07

import org.junit.Test

import com.example.ppb_mod2_kel07.model.Anime
import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun favorites_are_first_and_titles_can_sort_descending() {
        val anime = listOf(
            Anime(1, "Bleach", null, null, null, null, null, null, null),
            Anime(2, "Attack on Titan", null, null, null, null, null, null, null),
            Anime(3, "Cowboy Bebop", null, null, null, null, null, null, null)
        )

        assertEquals(listOf("Cowboy Bebop", "Bleach", "Attack on Titan"),
            filterAndSortAnime(anime, "", ascending = false, favorites = setOf(3)).map { it.title })
    }
}
