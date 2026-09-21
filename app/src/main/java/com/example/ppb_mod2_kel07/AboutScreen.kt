package com.example.ppb_mod2_kel07

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "About Page",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Aplikasi Praktikum PPB dengan Jetpack Compose",
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Dibuat oleh Kelompok 07, Shift 01",
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Anggota Kelompok:"
                )

                Text(
                    text = "Galileo Athari M. 21120124130099",
                    textAlign = TextAlign.Left
                )

                Text(
                    text = "Rayhan Cahya Qurnia 21120124130046",
                    textAlign = TextAlign.Left
                )

                Text(
                    text = "Cristian Duta D.S. 21120124140136",
                    textAlign = TextAlign.Left
                )

                Text(
                    text = "Alif Rizki K. Hariadi 21120124140148",
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AboutScreenPreview() {
    MaterialTheme {
        AboutScreen()
    }
}

