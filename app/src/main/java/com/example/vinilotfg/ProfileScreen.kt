package com.example.vinilotfg

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vinilotfg.ui.AppFooter
import com.example.vinilotfg.ui.AppHeader

@Composable
fun ClientesScreen(navController: NavController) {
    val fondoOscuro = Color(0xFF120338)
    val degradadoAvatar = Brush.linearGradient(
        colors = listOf(Color(0xFFE91E63), Color(0xFF9C27B0))
    )

    Scaffold(
        topBar = { AppHeader(title = "Perfil") },
        bottomBar = { AppFooter(navController) },
        containerColor = fondoOscuro
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(degradadoAvatar),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, "Avatar", Modifier.size(60.dp), Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Usuario", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("usuario@gmail.com", color = Color.LightGray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(12.dp))

            // Etiqueta Premium
            Surface(color = Color(0xFF311B92), shape = RoundedCornerShape(20.dp)) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically // Verifica que el import sea androidx.compose.ui.Alignment
                ) {
                    Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Usuario Premium", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Cards de estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(Modifier.weight(1f), "1,234", "Canciones")
                StatCard(Modifier.weight(1f), "45", "Playlists")
                StatCard(Modifier.weight(1f), "892", "Favoritas")
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Lista de opciones
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OptionRow(Icons.Default.PersonOutline, "Editar perfil")
                OptionRow(Icons.Default.FavoriteBorder, "Mis favoritas")
                OptionRow(Icons.Default.QueueMusic, "Mis playlists")
                OptionRow(Icons.Default.WorkspacePremium, "Obtener Premium", Color(0xFFFFD700))
                OptionRow(Icons.Default.Settings, "Configuración")
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier, value: String, label: String) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E0B4F)),
        border = BorderStroke(1.dp, Color(0xFF311B92))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(label, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun OptionRow(icon: ImageVector, title: String, iconColor: Color = Color.White) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E0B4F)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF311B92))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF120338), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, color = Color.White, modifier = Modifier.weight(1f), fontSize = 16.sp)
            Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
        }
    }
}