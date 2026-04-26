package com.example.vinilotfg

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import com.example.vinilotfg.ui.AppHeader
import com.example.vinilotfg.ui.AppFooter
import kotlinx.coroutines.delay

@Composable
fun StoreScreen(username: String?) {
    var searchQuery by remember { mutableStateOf("") }
    var isGrid by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val filteredVinyls = vinylList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.artist.contains(searchQuery, ignoreCase = true) ||
                it.genre.contains(searchQuery, ignoreCase = true)
    }

    val fondoDegradado = Brush.linearGradient(
        colors = listOf(Color(0xFF071A27), Color(0xFF1A3A4D)),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Scaffold(
        topBar = { AppHeader(title = "Vinyl Sound") }, // <--- CABECERA INTEGRADA
        bottomBar = { AppFooter() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDegradado)
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            if (!username.isNullOrEmpty()) {
                Text("Bienvenido, $username", style = MaterialTheme.typography.headlineSmall, color = Color.White, modifier = Modifier.padding(bottom = 16.dp))
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                placeholder = { Text("Buscar vinilos...", color = Color.LightGray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = Color(0xFF7C4DFF), unfocusedBorderColor = Color.Gray)
            )

            if (searchQuery.isEmpty()) {
                FeaturedCarousel(vinyls = vinylList.take(8))
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Catálogo", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                IconButton(onClick = { isGrid = !isGrid }) {
                    Icon(imageVector = if (isGrid) Icons.Filled.List else Icons.Filled.GridView, contentDescription = "Cambiar vista", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isGrid) {
                filteredVinyls.chunked(2).forEach { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowItems.forEach { vinyl -> Box(modifier = Modifier.weight(1f)) { VinylItem(vinyl, true) } }
                        if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            } else {
                filteredVinyls.forEach { vinyl ->
                    VinylItem(vinyl = vinyl, isGrid = false)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun FeaturedCarousel(vinyls: List<Vinyl>) {
    val listState = rememberLazyListState()
    LaunchedEffect(vinyls) {
        var index = 0
        while (true) {
            delay(3000)
            index = (index + 1) % vinyls.size
            listState.animateScrollToItem(index)
        }
    }
    Column {
        Text("Destacados", style = MaterialTheme.typography.titleLarge, color = Color.White, modifier = Modifier.padding(bottom = 12.dp))
        LazyRow(state = listState, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(vinyls.size) { i ->
                Image(
                    painter = painterResource(id = vinyls[i].imageRes),
                    contentDescription = vinyls[i].title,
                    modifier = Modifier.size(150.dp).clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun VinylItem(vinyl: Vinyl, isGrid: Boolean) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        if (isGrid) {
            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = vinyl.imageRes), contentDescription = vinyl.title, modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                Text(vinyl.title, maxLines = 1)
                Text("${vinyl.price} €", style = MaterialTheme.typography.bodySmall)
            }
        } else {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = vinyl.imageRes), contentDescription = vinyl.title, modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(vinyl.title, style = MaterialTheme.typography.titleMedium)
                    Text("Artista: ${vinyl.artist}")
                    Text("Precio: ${vinyl.price} €")
                }
            }
        }
    }
}