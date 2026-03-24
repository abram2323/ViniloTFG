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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinilotfg.ui.theme.*

/* ---------------------------------------------------
   ACTIVITY: Punto de entrada de la aplicación
--------------------------------------------------- */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite que el diseño se extienda bajo las barras del sistema (estado/navegación)
        enableEdgeToEdge()
        setContent {
            // Aplicación del tema global personalizado
            ViniloTFGTheme {
                // Inicialización del controlador de navegación
                val navController = rememberNavController()

                // Estructura base que gestiona el espacio de la pantalla
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Configuración del motor de rutas (NavHost)
                    NavHost(
                        navController = navController,
                        startDestination = "inicio", // Pantalla de carga inicial
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Ruta para el Login
                        composable("inicio") {
                            InicioScreen(navController)
                        }
                        // Ruta para el Registro
                        composable("register") {
                            Registro(navController)
                        }
                        // Ruta para la tienda con argumento dinámico (nombre de usuario)
                        composable("store/{username}") { backStackEntry ->
                            val username =
                                backStackEntry.arguments?.getString("username")
                            StoreScreen(username)
                        }
                        // Ruta para la tienda sin usuario identificado
                        composable("store_guest") {
                            StoreScreen(null)
                        }
                    }
                }
            }
        }
    }
}

/* ---------------------------------------------------
   HEADER: Barra superior reutilizable
--------------------------------------------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = LogoTextStyle,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorHeader // Color definido en el tema
        )
    )
}

/* ---------------------------------------------------
   LOGIN / INICIO: Interfaz de acceso
--------------------------------------------------- */
@Composable
fun InicioScreen(navController: NavController) {

    // Estados reactivos para capturar la entrada del usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Definición del degradado de fondo (Vertical)
    val fondo = Brush.linearGradient(
        colors = listOf(
            Color(0xFF4B1173),
            Color(0xFF1A002D)
        ),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // Definición del degradado para los botones (Horizontal)
    val botonGradiente = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB13CFF),
            Color(0xFFFF2D6F)
        )
    )

    // Contenedor principal con fondo decorativo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // IDENTIDAD VISUAL: Logo y Slogan
            Text(
                text = "🎵 Vinyl Sounds",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style= LogoTextStyle
            )
            Text(
                text = "Tu música, tu estilo",
                fontSize = 14.sp,
                color = Color(0xFFC9B4E3)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // TARJETA DE FORMULARIO (Card)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF221137),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Bienvenido de nuevo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Ingresa tus credenciales para continuar",
                    fontSize = 13.sp,
                    color = Color(0xFFBFA7D8)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // ACCESO CON GOOGLE (Componente visual sin lógica activa)
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Continuar con Google",
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // SEPARADOR VISUAL
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFF2F1B4D),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "O continúa con",
                        fontSize = 11.sp,
                        color = Color(0xFFBFA7D8)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ENTRADA DE EMAIL
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

                // ENTRADA DE CONTRASEÑA (Con transformación visual oculta)
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

                // BOTÓN DE ACCIÓN: LOGIN
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(botonGradiente, RoundedCornerShape(20.dp))
                ) {
                    Button(
                        onClick = {
                            // Navegación hacia la tienda pasando el email si no está vacío
                            if (email.isNotBlank()) {
                                navController.navigate("store/$email")
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // ENLACE A REGISTRO
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    fontSize = 13.sp,
                    color = Color(0xFFBFA7D8),
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // OPCIÓN PARA ENTRAR SIN CUENTA
                Text(
                    text = "Entrar como invitado",
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate("store_guest")
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // TEXTO LEGAL / PIE DE PÁGINA
            Text(
                text = "Al continuar, aceptas nuestros Términos de servicio y Política de privacidad",
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 14.sp
            )
        }
    }
}

/* ---------------------------------------------------
   PREVIEW: Previsualización en el editor de Android Studio
--------------------------------------------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioScreenPreview() {
    ViniloTFGTheme {
        InicioScreen(navController = rememberNavController())
    }
}