package com.example.vinilotfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vinilotfg.ui.theme.ViniloTFGTheme
import androidx.compose.ui.tooling.preview.Preview

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
                        // Pantalla de inicio
                        composable("inicio") {
                            InicioScreen(navController)
                        }

                        // Pantalla de registro
                        composable("register") {
                            Registro(navController)
                        }

                        // Tienda para usuario registrado
                        composable("store/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username")
                            StoreScreen(username)
                        }

                        // Tienda para invitado
                        composable("store_guest") {
                            StoreScreen(null)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InicioScreenPreview() {
    ViniloTFGTheme {
        val navController = rememberNavController()
        InicioScreen(navController)
    }
}
@Composable
fun InicioScreen(navController: androidx.navigation.NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Vinyl Sounds",
                style = MaterialTheme.typography.headlineMedium
            )

            // Campo de usuario
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email o usario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )



            // Botón Entrar (usuario)
            // Fila con Entrar y Registrarse
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Entrar")
                }

                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Registrarse")
                }
            }

// Botón Invitado debajo
            Button(
                onClick = { navController.navigate("store_guest") },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Entrar como invitado")
            }

        }
    }
}
