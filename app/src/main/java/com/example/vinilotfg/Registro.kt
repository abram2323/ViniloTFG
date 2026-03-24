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

/* ---------------------------------------------------
   PANTALLA DE REGISTRO
--------------------------------------------------- */
@Composable
fun Registro(navController: NavController) {

    // Estados para almacenar el texto introducido en cada campo del formulario
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    // Definición del degradado lineal para el fondo de la pantalla (púrpura a negro)
    val fondo = Brush.linearGradient(
        colors = listOf(
            Color(0xFF4B1173),
            Color(0xFF1A002D)
        ),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // Definición del degradado horizontal para el botón principal
    val botonGradiente = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB13CFF),
            Color(0xFFFF2D6F)
        )
    )

    // Contenedor principal que ocupa toda la pantalla con el fondo degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo),
        contentAlignment = Alignment.TopCenter
    ) {

        // Columna que organiza el logo y la tarjeta de registro verticalmente
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // SECCIÓN LOGO
            Text(
                text = "🎵 Vinyl Sounds",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style= LogoTextStyle
            )
            Text(
                text = "Crea tu cuenta",
                fontSize = 14.sp,
                color = Color(0xFFC9B4E3)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // CARD: Contenedor oscuro con bordes redondeados para el formulario
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
                    text = "Registro",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Únete a Vinyl Sounds",
                    fontSize = 13.sp,
                    color = Color(0xFFBFA7D8)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // FILA PARA NOMBRE Y APELLIDO (Dividen el ancho al 50% usando weight)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = registroTextFieldColors()
                    )

                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        placeholder = { Text("Apellido") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = registroTextFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // CAMPO EMAIL
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = registroTextFieldColors()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // CAMPO CONTRASEÑA (Usa PasswordVisualTransformation para ocultar caracteres)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = registroTextFieldColors()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // CAMPO REPETIR CONTRASEÑA
                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    placeholder = { Text("Repite la contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = registroTextFieldColors()
                )

                Spacer(modifier = Modifier.height(22.dp))

                // BOTÓN CREAR CUENTA CON FONDO DEGRADADO
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(botonGradiente, RoundedCornerShape(20.dp))
                ) {
                    Button(
                        onClick = {
                            if (email.isNotBlank()) {
                                // Navega a la tienda y elimina la pantalla de login del historial
                                navController.navigate("store/$email") {
                                    popUpTo("inicio") { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent // Permite ver el degradado del Box
                        )
                    ) {
                        Text(
                            text = "Crear cuenta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TEXTO CLICKABLE PARA REGRESAR AL LOGIN
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    fontSize = 13.sp,
                    color = Color(0xFFBFA7D8),
                    modifier = Modifier.clickable {
                        navController.popBackStack() // Vuelve a la pantalla anterior
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // PIE DE PÁGINA: Texto legal
            Text(
                text = "Al registrarte, aceptas nuestros Términos de servicio y Política de privacidad",
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 14.sp
            )
        }
    }
}

/* ---------------------------------------------------
   CONFIGURACIÓN DE COLORES REUTILIZABLE PARA INPUTS
--------------------------------------------------- */
@Composable
fun registroTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color(0xFF7B5CFF),
    unfocusedBorderColor = Color(0xFF3E2A5E),
    focusedPlaceholderColor = Color(0xFFBFA7D8),
    unfocusedPlaceholderColor = Color(0xFFBFA7D8)
)

/* ---------------------------------------------------
   VISTA PREVIA DEL COMPONENTE
--------------------------------------------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroPreview() {
    ViniloTFGTheme {
        Registro(navController = rememberNavController())
    }
}