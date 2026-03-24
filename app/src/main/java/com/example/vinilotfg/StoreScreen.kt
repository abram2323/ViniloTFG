package com.example.vinilotfg

import androidx.compose.animation.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vinilotfg.ui.AppHeader
import com.example.vinilotfg.ui.AppFooter
import com.example.vinilotfg.ui.theme.ViniloTFGTheme
import kotlinx.coroutines.delay

/* ---------------------------------------------------
   PREVIEW: Permite visualizar la tienda en el IDE con datos de prueba
--------------------------------------------------- */
@Preview(showBackground = true)
@Composable
fun StorePreviewFakeData() {
    ViniloTFGTheme {
        StoreScreen(username = "Carlos")
    }
}

/* ---------------------------------------------------
   STORE SCREEN: Pantalla principal del catálogo
--------------------------------------------------- */
@Composable
fun StoreScreen(username: String?) {

    // Estados para controlar la búsqueda y el tipo de vista (Cuadrícula o Lista)
    var searchQuery by remember { mutableStateOf("") }
    var isGrid by remember { mutableStateOf(false) }

    // Estado para permitir el scroll vertical de toda la pantalla
    val scrollState = rememberScrollState()

    // Lógica de filtrado: busca coincidencias en título, artista o género
    val filteredVinyls = vinylList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.artist.contains(searchQuery, ignoreCase = true) ||
                it.genre.contains(searchQuery, ignoreCase = true)
    }

    // Definición del degradado de fondo para la pantalla
    val fondoDegradado = Brush.linearGradient(
        colors = listOf(Color(0xFF071A27), Color(0xFF1A3A4D)),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // Estructura principal con barra superior y barra de navegación inferior
    Scaffold(
        topBar = { AppHeader(title = "Vinyl Sound") },
        bottomBar = { AppFooter() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoDegradado)
                .padding(paddingValues)
                .verticalScroll(scrollState)   // Habilita el desplazamiento vertical
                .padding(16.dp)
        ) {

            // Muestra mensaje de bienvenida si el usuario no es invitado
            if (!username.isNullOrEmpty()) {
                Text(
                    text = "Bienvenido, $username",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            /* -------- BUSCADOR -------- */
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                placeholder = {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Buscar vinilos...",
                            color = Color.LightGray
                        )
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF7C4DFF),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                )
            )

            /* -------- DESTACADOS (Solo se ve si no se está filtrando nada) -------- */
            if (searchQuery.isEmpty()) {
                AutoScrollingFeaturedCarousel(
                    vinyls = filteredVinyls.take(8)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            /* -------- CABECERA CATÁLOGO: Título y botón de cambio de vista -------- */
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

            /* -------- CATÁLOGO: Renderizado según el modo (Grid o Lista) -------- */
            if (isGrid) {
                // Modo Cuadrícula: Agrupa los vinilos de 2 en 2 para crear filas
                filteredVinyls.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { vinyl ->
                            Box(modifier = Modifier.weight(1f)) {
                                VinylItem(vinyl, isGrid = true)
                            }
                        }
                        // Espaciador para cuando queda un solo elemento en la última fila
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else {
                // Modo Lista: Muestra un vinilo por cada fila
                filteredVinyls.forEach { vinyl ->
                    VinylItem(vinyl = vinyl, isGrid = false)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Espacio final para evitar que el contenido quede oculto tras el AppFooter
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

/* ---------------------------------------------------
   CARRUSEL AUTOMÁTICO: Carrusel que se desplaza solo
--------------------------------------------------- */
@Composable
fun AutoScrollingFeaturedCarousel(vinyls: List<Vinyl>) {

    val listState = rememberLazyListState()

    // LaunchedEffect: Efecto que gestiona el movimiento automático infinito
    LaunchedEffect(vinyls) {
        if (vinyls.isNotEmpty()) {
            var index = 0
            while (true) {
                delay(3000) // Pausa de 3 segundos entre cada movimiento
                index = (index + 1) % vinyls.size
                listState.animateScrollToItem(index) // Animación suave al siguiente item
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(vinyls.size) { i ->
                val vinyl = vinyls[i]
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
    }
}

/* ---------------------------------------------------
   ITEM VINILO: Componente para mostrar un disco individualmente
--------------------------------------------------- */
@Composable
fun VinylItem(vinyl: Vinyl, isGrid: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        if (isGrid) {
            // Diseño para la vista de cuadrícula (Elementos apilados verticalmente)
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

                Text(vinyl.title, maxLines = 1)
                Text(vinyl.artist, style = MaterialTheme.typography.bodySmall)
                Text(vinyl.genre, style = MaterialTheme.typography.bodySmall)
                Text("${vinyl.price} €", style = MaterialTheme.typography.bodySmall)
            }
        } else {
            // Diseño para la vista de lista (Imagen a la izquierda, texto a la derecha)
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