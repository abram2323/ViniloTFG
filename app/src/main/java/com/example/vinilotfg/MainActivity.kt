package com.example.vinilotfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinilotfg.ui.theme.*

/**
 * Actividad principal de la aplicación.
 * Configura el sistema de navegación y el tema general.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el diseño de borde a borde (transparencia en barras de sistema)
        enableEdgeToEdge()

        setContent {
            ViniloTFGTheme {
                // Gestiona el estado de la navegación entre pantallas
                val navController = rememberNavController()

                // Definición de las rutas de navegación de la app
                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {
                    // Pantalla principal de Login
                    composable("inicio") { InicioScreen(navController) }

                    // Pantalla de Registro de usuario
                    composable("register") { Registro(navController) }

                    // Pantalla de Tienda recibiendo el nombre de usuario como argumento
                    composable("store/{username}") { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username")
                        StoreScreen(username, navController)
                    }

                    // Acceso a la tienda sin usuario identificado
                    composable("store_guest") {
                        StoreScreen(null, navController)
                    }

                    // Pantalla de gestión de perfil/clientes
                    composable("perfil") {
                        ClientesScreen(navController)
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de inicio de sesión con diseño personalizado.
 * @param navController Controlador para manejar los saltos entre pantallas.
 */
@Composable
fun InicioScreen(navController: NavController) {
    // Estados reactivos para capturar lo que el usuario escribe
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Definición del degradado de fondo (Púrpura oscuro a negro)
    val fondo = Brush.linearGradient(
        colors = listOf(Color(0xFF4B1173), Color(0xFF1A002D)),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // Definición del degradado para el botón principal (Lila a Rosa)
    val botonGradiente = Brush.horizontalGradient(
        colors = listOf(Color(0xFFB13CFF), Color(0xFFFF2D6F))
    )

    // Contenedor principal que ocupa toda la pantalla
    Box(
        modifier = Modifier.fillMaxSize().background(fondo),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Cabecera: Logo y Slogan
            Text("🎵 Vinyl Sounds", fontSize = 30.sp, fontWeight = FontWeight.Bold, style = LogoTextStyle)
            Text("Tu música, tu estilo", fontSize = 14.sp, color = Color(0xFFC9B4E3))

            Spacer(modifier = Modifier.height(36.dp))

            // Tarjeta central de login
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF221137), RoundedCornerShape(30.dp))
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bienvenido de nuevo", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Ingresa tus credenciales para continuar", fontSize = 13.sp, color = Color(0xFFBFA7D8))

                Spacer(modifier = Modifier.height(18.dp))

                // Botón de Google
                OutlinedButton(
                    onClick = { /* Pendiente: Lógica de OAuth Google */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Text("Continuar con Google", color = Color.Black, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Separador visual "O continúa con"
                Box(modifier = Modifier.background(Color(0xFF2F1B4D), RoundedCornerShape(20.dp)).padding(horizontal = 14.dp, vertical = 4.dp)) {
                    Text("O continúa con", fontSize = 11.sp, color = Color(0xFFBFA7D8))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email o usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF7B5CFF),
                        unfocusedBorderColor = Color(0xFF3E2A5E),
                        focusedPlaceholderColor = Color(0xFFBFA7D8),
                        unfocusedPlaceholderColor = Color(0xFFBFA7D8)
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Campo de texto para Contraseña (con ocultación de caracteres)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF7B5CFF),
                        unfocusedBorderColor = Color(0xFF3E2A5E),
                        focusedPlaceholderColor = Color(0xFFBFA7D8),
                        unfocusedPlaceholderColor = Color(0xFFBFA7D8)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botón de "Iniciar sesión" con el degradado personalizado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(botonGradiente, RoundedCornerShape(20.dp))
                        .clickable {
                            // Navega a la tienda pasando el email como parámetro si no está vacío
                            if (email.isNotBlank()) navController.navigate("store/$email")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Iniciar sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Enlaces secundarios de navegación
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    fontSize = 13.sp,
                    color = Color(0xFFBFA7D8),
                    modifier = Modifier.clickable { navController.navigate("register") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Entrar como invitado",
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.clickable { navController.navigate("store_guest") }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Pie de página con términos legales
            Text(
                text = "Al continuar, aceptas nuestros Términos de servicio y Política de privacidad",
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 14.sp
            )
        }
    }
}