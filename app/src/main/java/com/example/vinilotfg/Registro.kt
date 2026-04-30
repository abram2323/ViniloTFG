package com.example.vinilotfg

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
import androidx.navigation.compose.rememberNavController
import com.example.vinilotfg.ui.theme.*

/**
 * Pantalla de registro de nuevos usuarios.
 *
 * @param navController Objeto encargado de gestionar la navegación entre pantallas.
 */
@Composable
fun Registro(navController: NavController) {

    // Estados reactivos: mantienen el texto escrito por el usuario en tiempo real
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    // Definición de degradado lineal para el fondo de pantalla
    val fondo = Brush.linearGradient(
        colors = listOf(Color(0xFF4B1173), Color(0xFF1A002D)),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // Definición de degradado horizontal para el botón principal
    val botonGradiente = Brush.horizontalGradient(
        colors = listOf(Color(0xFFB13CFF), Color(0xFFFF2D6F))
    )

    // Contenedor principal con fondo degradado
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
            Text("🎵 Vinyl Sounds", fontSize = 30.sp, fontWeight = FontWeight.Bold, style = LogoTextStyle)
            Text("Crea tu cuenta", fontSize = 14.sp, color = Color(0xFFC9B4E3))
            Spacer(modifier = Modifier.height(36.dp))

            // Tarjeta contenedora del formulario de registro
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF221137), RoundedCornerShape(30.dp))
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Registro", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(18.dp))

                // Fila para Nombre y Apellido compartiendo ancho al 50% cada uno mediante .weight(1f)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = { Text("Nombre") },
                        modifier = Modifier.weight(1f),
                        colors = registroTextFieldColors()
                    )
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        placeholder = { Text("Apellido") },
                        modifier = Modifier.weight(1f),
                        colors = registroTextFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Campos de entrada de datos a ancho completo
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = registroTextFieldColors()
                )
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(), // Oculta la contraseña
                    modifier = Modifier.fillMaxWidth(),
                    colors = registroTextFieldColors()
                )
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    placeholder = { Text("Repite la contraseña") },
                    visualTransformation = PasswordVisualTransformation(), // Oculta la repetición de contraseña
                    modifier = Modifier.fillMaxWidth(),
                    colors = registroTextFieldColors()
                )

                Spacer(modifier = Modifier.height(22.dp))

                // BOTÓN DE REGISTRO con fondo degradado personalizado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(botonGradiente, RoundedCornerShape(20.dp))
                ) {
                    Button(
                        onClick = {
                            if (email.isNotBlank()) {
                                // Navega a la tienda y limpia el historial de navegación para no poder volver al registro
                                navController.navigate("store/$email") {
                                    popUpTo("inicio") { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Fondo transparente para ver el Box
                    ) {
                        Text("Crear cuenta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Opción para volver atrás en el stack de navegación (pantalla de inicio/login)
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    fontSize = 13.sp,
                    color = Color(0xFFBFA7D8),
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Footer legal
            Text(
                text = "Al registrarte, aceptas nuestros Términos de servicio y Política de privacidad",
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 14.sp
            )
        }
    }
}

/**
 * Función auxiliar para reutilizar los estilos de color en los OutlinedTextField del registro.
 * Define colores específicos para el borde, texto y placeholders en estados enfocado/desenfocado.
 */
@Composable
fun registroTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color(0xFF7B5CFF),
    unfocusedBorderColor = Color(0xFF3E2A5E),
    focusedPlaceholderColor = Color(0xFFBFA7D8),
    unfocusedPlaceholderColor = Color(0xFFBFA7D8)
)

/**
 * Vista previa para el diseñador de Android Studio.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroPreview() {
    ViniloTFGTheme {
        Registro(navController = rememberNavController())
    }
}