package com.example.vinilotfg

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.vinilotfg.ui.theme.ViniloTFGTheme


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroPreview() {
    ViniloTFGTheme {
        val navController = rememberNavController()
        Registro(navController)
    }
}

@Composable
fun Registro(navController: NavController) {

    var nombre by remember {mutableStateOf( value = "")}
    var apellido by remember {mutableStateOf( value = "")}

    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var repiteContraseña by remember {mutableStateOf( value = "")}

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "Registro",
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contraseña,
                onValueChange = { contraseña = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = repiteContraseña,
                onValueChange = { repiteContraseña = it },
                label = { Text("Repite contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para crear cuenta y navegar a la tienda
            Button(
                onClick = {
                    if (email.isNotBlank()) {
                        // Navega a StoreScreen pasando el username
                        navController.navigate("store/$email") {
                            // Evita volver a la pantalla de registro
                            popUpTo("inicio") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear cuenta")
            }

            TextButton(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
    }
}
