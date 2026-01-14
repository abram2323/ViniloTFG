package com.example.vinilotfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.example.vinilotfg.ui.theme.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
/* ---------------- ACTIVITY ---------------- */

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
                        composable("inicio") {
                            InicioScreen(navController)
                        }
                        composable("register") {
                            Registro(navController)
                        }
                        composable("store/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username")
                            StoreScreen(username)
                        }
                        composable("store_guest") {
                            StoreScreen(null)
                        }
                    }
                }
            }
        }
    }
}

/* ---------------- HEADER ---------------- */

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
            containerColor = ColorHeader
        )
    )
}

/* ---------------- PREVIEW ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioScreenPreview() {
    ViniloTFGTheme {
        val navController = rememberNavController()
        InicioScreen(navController)
    }
}

/* ---------------- PANTALLA INICIO ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(navController: androidx.navigation.NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Definimos el degradado de fondo
    val fondoDegradado = Brush.linearGradient(
        0.0f to Color(0xFF071A27),
        1.0f to Color(0xFF1A3A4D),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Scaffold(
        // Ponemos el contenedor del Scaffold transparente para que mande el Box
        containerColor = Color.Transparent,
        topBar = {
            AppHeader(title = "Vinyl Sounds")
        }
    ) { paddingValores -> // <--- Este es el innerPadding que entrega el Scaffold

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = fondoDegradado),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(paddingValores) // <--- USAMOS EL PADDING AQUÍ PARA EL CONTENIDO
                    .padding(20.dp)          // Padding extra para los bordes
            ) {

                Text(
                    text = "Bienvenido de nuevo",
                    color = Purple60,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Text(
                    text = "Ingresa tus credenciales",
                    color = TextoBlanco,
                    modifier = Modifier.align(Alignment.Start)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email o usuario", color = Color.LightGray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextoBlanco,
                        unfocusedTextColor = TextoBlanco,
                        focusedBorderColor = Color(0xFFA350F9), // Morado del logo
                        unfocusedBorderColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Contraseña", color = Color.LightGray) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextoBlanco,
                        unfocusedTextColor = TextoBlanco,
                        focusedBorderColor = Color(0xFFA350F9),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (email.isNotBlank()) {
                                navController.navigate("store/$email")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                    ) {
                        Text("Iniciar sesión")
                    }

                    Button(
                        onClick = { navController.navigate("register") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                    ) {
                        Text("Registrarse")
                    }
                }

                Button(
                    onClick = { navController.navigate("store_guest") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorTranparente)
                ) {
                    Text("Entrar como invitado")
                }
            }
        }
    }
}

