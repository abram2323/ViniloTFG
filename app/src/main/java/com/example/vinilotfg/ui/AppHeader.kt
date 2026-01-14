package com.example.vinilotfg.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vinilotfg.ui.theme.*
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = TextoBlanco,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorHeader
        )
    )
}
