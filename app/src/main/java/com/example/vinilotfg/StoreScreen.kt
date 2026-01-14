package com.example.vinilotfg

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vinilotfg.ui.theme.ViniloTFGTheme
import kotlinx.coroutines.delay

/* ---------------------------------------------------
   PREVIEW
--------------------------------------------------- */
@Preview(showBackground = true)
@Composable
fun StorePreviewFakeData() {
    ViniloTFGTheme {
        StoreScreen(username = "Carlos")
    }
}

/* ---------------------------------------------------
   STORE SCREEN
--------------------------------------------------- */
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

        // 👋 BIENVENIDA
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
                .padding(bottom = 20.dp),
            placeholder = { Text("Buscar vinilos...") },
            singleLine = true
        )

        // ⭐ CARRUSEL AUTOMÁTICO
        AutoScrollingFeaturedCarousel(
            vinyls = filteredVinyls.take(5)
        )

        // 🔘 BOTONES DESLIZABLES ENTRE CARRUSEL Y CATÁLOGO
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            item {
                FilledTonalButton(onClick = { }) {
                    Text("Rock")
                }
            }
            item {
                FilledTonalButton(onClick = { }) {
                    Text("Pop")
                }
            }
            item {
                FilledTonalButton(onClick = { }) {
                    Text("Trap/Hip hop")
                }
            }
            item {
                FilledTonalButton(onClick = { }) {
                    Text("R&B")
                }
            }
            item {
                FilledTonalButton(onClick = { }) {
                    Text("Reguetón")
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // 📀 CATÁLOGO
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

/* ---------------------------------------------------
   CARRUSEL AUTOMÁTICO
--------------------------------------------------- */
@Composable
fun AutoScrollingFeaturedCarousel(vinyls: List<Vinyl>) {
    val listState = rememberLazyListState()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(vinyls) {
        visible = true
        if (vinyls.isNotEmpty()) {
            var index = 0
            while (true) {
                delay(3000L)
                index = (index + 1) % vinyls.size
                listState.animateScrollToItem(index)
            }
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically { it / 2 }
    ) {
        Column {
            Text(
                text = "Destacados",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                itemsIndexed(vinyls) { index, vinyl ->
                    AnimatedVinylCard(vinyl, index)
                }
            }
        }
    }
}

/* ---------------------------------------------------
   TARJETA ANIMADA
--------------------------------------------------- */
@Composable
fun AnimatedVinylCard(vinyl: Vinyl, index: Int) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 120L)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally { it / 2 }
    ) {
        Card(
            modifier = Modifier.width(160.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = vinyl.imageRes),
                    contentDescription = vinyl.title,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = vinyl.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1
                )

                Text(
                    text = vinyl.artist,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

/* ---------------------------------------------------
   ITEM LISTA VERTICAL
--------------------------------------------------- */
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

            Image(
                painter = painterResource(id = vinyl.imageRes),
                contentDescription = vinyl.title,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(vinyl.title, style = MaterialTheme.typography.titleMedium)
                Text("Artista: ${vinyl.artist}")
                Text("Género: ${vinyl.genre}")
                Text("Precio: ${vinyl.price} €")
            }
        }
    }
}
