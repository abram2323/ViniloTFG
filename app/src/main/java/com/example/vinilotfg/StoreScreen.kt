package com.example.vinilotfg

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vinilotfg.ui.AppHeader
import com.example.vinilotfg.ui.AppFooter
import kotlinx.coroutines.delay

@Composable
fun StoreScreen(
    username: String?,
    navController: NavController,
    viewModel: VinylViewModel = viewModel()
) {
    val vinylList by viewModel.vinyls.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isGrid by remember { mutableStateOf(false) }

    val filteredVinyls = vinylList.filter {
        it.nombre.contains(searchQuery, ignoreCase = true) ||
                it.artista.contains(searchQuery, ignoreCase = true)
    }

    val fondoDegradado = Brush.linearGradient(
        colors = listOf(Color(0xFF071A27), Color(0xFF1A3A4D)),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Scaffold(
        topBar = { AppHeader(title = "Vinyl Sound") },
        bottomBar = { AppFooter(navController) }
    ) { paddingValues ->

        if (vinylList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF071A27)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(fondoDegradado)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (!username.isNullOrEmpty()) {
                    Text(
                        text = "Bienvenido, $username",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    placeholder = { Text("Buscar vinilos...", color = Color.LightGray) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.Cyan
                    )
                )

                if (searchQuery.isEmpty()) {
                    FeaturedCarousel(vinyls = vinylList.take(5))
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Catálogo", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                    IconButton(onClick = { isGrid = !isGrid }) {
                        Icon(
                            imageVector = if (isGrid) Icons.Filled.List else Icons.Filled.GridView,
                            contentDescription = "Cambiar vista",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isGrid) {
                        items(filteredVinyls.chunked(2)) { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowItems.forEach { vinyl ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        VinylItem(vinyl, true, navController)
                                    }
                                }
                                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    } else {
                        items(filteredVinyls) { vinyl ->
                            VinylItem(vinyl = vinyl, isGrid = false, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedCarousel(vinyls: List<Vinyl>) {
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            if (vinyls.isNotEmpty()) {
                val nextIndex = (listState.firstVisibleItemIndex + 1) % vinyls.size
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    Column {
        Text(
            text = "Destacados",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(vinyls) { vinyl ->
                AsyncImage(
                    model = vinyl.imagen_url,
                    contentDescription = vinyl.nombre,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun VinylItem(vinyl: Vinyl, isGrid: Boolean, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navegamos a la pantalla de detalle usando el ID de Supabase
                navController.navigate("detalle/${vinyl.id}")
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF221137)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isGrid) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = vinyl.imagen_url,
                    contentDescription = vinyl.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = vinyl.nombre,
                    color = Color.White,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${vinyl.precio} €",
                    color = Color.Cyan,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        } else {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = vinyl.imagen_url,
                    contentDescription = vinyl.nombre,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = vinyl.nombre,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Artista: ${vinyl.artista}",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "${vinyl.precio} €",
                        color = Color.Cyan,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}