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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViniloTFGTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "inicio",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("inicio") { InicioScreen(navController) }
                        composable("register") { Registro(navController) }
                        composable("store/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username")
                            StoreScreen(username)
                        }
                        composable("store_guest") { StoreScreen(null) }
                    }
                }
            }
        }
    }
}

@Composable
fun InicioScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val fondo = Brush.linearGradient(
        colors = listOf(Color(0xFF4B1173), Color(0xFF1A002D)),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    val botonGradiente = Brush.horizontalGradient(
        colors = listOf(Color(0xFFB13CFF), Color(0xFFFF2D6F))
    )

    Box(
        modifier = Modifier.fillMaxSize().background(fondo),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text("🎵 Vinyl Sounds", fontSize = 30.sp, fontWeight = FontWeight.Bold, style = LogoTextStyle)
            Text("Tu música, tu estilo", fontSize = 14.sp, color = Color(0xFFC9B4E3))

            Spacer(modifier = Modifier.height(36.dp))

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

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Text("Continuar con Google", color = Color.Black, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(modifier = Modifier.background(Color(0xFF2F1B4D), RoundedCornerShape(20.dp)).padding(horizontal = 14.dp, vertical = 4.dp)) {
                    Text("O continúa con", fontSize = 11.sp, color = Color(0xFFBFA7D8))
                }

                Spacer(modifier = Modifier.height(16.dp))

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

                Box(modifier = Modifier.fillMaxWidth().height(56.dp).background(botonGradiente, RoundedCornerShape(20.dp))) {
                    Button(
                        onClick = { if (email.isNotBlank()) navController.navigate("store/$email") },
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text("Iniciar sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("¿No tienes cuenta? Regístrate", fontSize = 13.sp, color = Color(0xFFBFA7D8), modifier = Modifier.clickable { navController.navigate("register") })
                Spacer(modifier = Modifier.height(8.dp))
                Text("Entrar como invitado", fontSize = 13.sp, color = Color.White, modifier = Modifier.clickable { navController.navigate("store_guest") })
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text("Al continuar, aceptas nuestros Términos de servicio y Política de privacidad", fontSize = 11.sp, color = Color.Gray, lineHeight = 14.sp)
        }
    }
}