package com.example.vinilotfg

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vinilotfg.ui.AppHeader
import com.example.vinilotfg.ui.AppFooter
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
    var isGrid by remember { mutableStateOf(false) } // Estado para cambiar entre lista/grid

    // Filtrado de vinilos según búsqueda
    val filteredVinyls = vinylList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.artist.contains(searchQuery, ignoreCase = true) ||
                it.genre.contains(searchQuery, ignoreCase = true)
    }

    // Degradado de fondo
    val fondoDegradado = Brush.linearGradient(
        colors = listOf(Color(0xFF071A27), Color(0xFF1A3A4D)),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // Scaffold con HEADER y FOOTER
    Scaffold(
        topBar = { AppHeader(title = "Vinyl Sound") },
        bottomBar = { AppFooter() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDegradado) // <-- Fondo degradado
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // 👋 Bienvenida
            if (!username.isNullOrEmpty()) {
                Text(
                    text = "Bienvenido, $username",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.White
                )
            }

            // 🔍 Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                placeholder = { Text("Buscar vinilos...") },
                singleLine = true
            )

            // ⭐ Carrusel de destacados
            AutoScrollingFeaturedCarousel(
                vinyls = filteredVinyls.take(8)
            )

            // 🔘 Filtros
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                item { FilledTonalButton(onClick = { }) { Text("Rock") } }
                item { FilledTonalButton(onClick = { }) { Text("Pop") } }
                item { FilledTonalButton(onClick = { }) { Text("Trap/Hip hop") } }
                item { FilledTonalButton(onClick = { }) { Text("R&B") } }
                item { FilledTonalButton(onClick = { }) { Text("Reguetón") } }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📀 Catálogo con botón de cambio de vista
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Catálogo de Vinilos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                IconButton(onClick = { isGrid = !isGrid }) {
                    Icon(
                        imageVector = if (isGrid) Icons.Filled.List else Icons.Filled.GridView,
                        contentDescription = "Cambiar vista",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar lista o grid según isGrid
            if (isGrid) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredVinyls) { vinyl ->
                        VinylItem(vinyl, isGrid = true)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredVinyls) { vinyl ->
                        VinylItem(vinyl, isGrid = false)
                    }
                }
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
                modifier = Modifier.padding(bottom = 12.dp),
                color = Color.White
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
   TARJETA ANIMADA (solo imagen)
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
        Image(
            painter = painterResource(id = vinyl.imageRes),
            contentDescription = vinyl.title,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

/* ---------------------------------------------------
   ITEM DE VINILO (Lista o Grid)
--------------------------------------------------- */
@Composable
fun VinylItem(vinyl: Vinyl, isGrid: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        if (isGrid) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = vinyl.imageRes),
                    contentDescription = vinyl.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(vinyl.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(vinyl.artist, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Text(vinyl.genre, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                Text("${vinyl.price} €", style = MaterialTheme.typography.bodySmall)
            }
        } else {
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
}
