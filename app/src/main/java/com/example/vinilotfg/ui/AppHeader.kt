package com.example.vinilotfg.ui

import android.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                fontWeight = FontWeight.Bold,
                style= LogoTextStyle
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorHeader
        )
    )
}
