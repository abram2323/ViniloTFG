package com.example.vinilotfg

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.vinilotfg.ui.theme.*

/* ---------------- PREVIEW ---------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroPreview() {
    ViniloTFGTheme {
        val navController = rememberNavController()
        Registro(navController)
    }
}

/* ---------------- PANTALLA REGISTRO ---------------- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var repiteContraseña by remember { mutableStateOf("") }

    // Definimos el mismo degradado que en el Inicio
    val fondoDegradado = Brush.linearGradient(
        0.0f to Color(0xFF071A27),
        1.0f to Color(0xFF1A3A4D),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // PASO 1: Box principal con el degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = fondoDegradado)
    ) {
        // PASO 2: Scaffold transparente
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { AppHeader(title = "Registro") }
        ) { innerPadding ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
            ) {

                Text(
                    text = "Crear cuenta",
                    color = Purple60,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Únete a VinylSounds",
                    color = TextoBlanco,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = { Text("Nombre", color = Color.LightGray) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextoBlanco,
                            unfocusedTextColor = TextoBlanco,
                            focusedBorderColor = Color(0xFFA350F9),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        placeholder = { Text("Apellido", color = Color.LightGray) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextoBlanco,
                            unfocusedTextColor = TextoBlanco,
                            focusedBorderColor = Color(0xFFA350F9),
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Correo electrónico", color = Color.LightGray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextoBlanco,
                        unfocusedTextColor = TextoBlanco,
                        focusedBorderColor = Color(0xFFA350F9),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    placeholder = { Text("Contraseña", color = Color.LightGray) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextoBlanco,
                        unfocusedTextColor = TextoBlanco,
                        focusedBorderColor = Color(0xFFA350F9),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = repiteContraseña,
                    onValueChange = { repiteContraseña = it },
                    placeholder = { Text("Repite contraseña", color = Color.LightGray) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextoBlanco,
                        unfocusedTextColor = TextoBlanco,
                        focusedBorderColor = Color(0xFFA350F9),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (email.isNotBlank()) {
                            navController.navigate("store/$email") {
                                popUpTo("inicio") { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                ) {
                    Text("Crear cuenta", fontWeight = FontWeight.Bold)
                }

                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Volver", color = TextoBlanco)
                }
            }
        }
    }
}