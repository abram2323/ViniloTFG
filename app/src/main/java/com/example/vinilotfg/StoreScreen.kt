package com.example.vinilotfg

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilotfg.ui.theme.ViniloTFGTheme
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*




@Preview
@Composable
fun StorePreviewFakeData() {
    ViniloTFGTheme {
        StoreScreen(
        username = "Carlos")
    }
}

@Composable
fun StoreScreen(username: String?) {

    var searchQuery by remember { mutableStateOf("") }

    val filteredVinyls = vinylList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.artist.contains(searchQuery, ignoreCase = true) ||
                it.genre.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (!username.isNullOrEmpty()) {
            Text(
                text = "Bienvenido, $username",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // 🔍 BARRA DE BÚSQUEDA
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Buscar vinilos...") },
            singleLine = true
        )

        Text(
            text = "Catálogo de Vinilos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredVinyls) { vinyl ->
            VinylItem(vinyl)
            }
        }
    }
}


@Composable
fun VinylItem(vinyl: Vinyl) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            // IMAGEN GRANDE (una sola)
            Image(
                painter = painterResource(id = vinyl.imageRes),
                contentDescription = vinyl.title,
                modifier = Modifier
                    .width(110.dp)              // ancho fijo
                    .height(110.dp)             // altura fija (portada)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // TEXTO A LA DERECHA
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = vinyl.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text("Artista: ${vinyl.artist}")
                Text("Género: ${vinyl.genre}")
                Text("Precio: ${vinyl.price} €")
            }
        }
    }
}


