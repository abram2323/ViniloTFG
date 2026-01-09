package com.example.vinilotfg

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.vinilotfg.ui.theme.ViniloTFGTheme
import kotlin.math.abs
import androidx.compose.ui.layout.ContentScale


@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    ViniloTFGTheme {
        InicioScreen()
    }
}


@Composable
fun InicioScreen() {

    val listState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(horizontal = 64.dp)
        ) {
            itemsIndexed(vinylList) { index, vinyl ->

                val centerIndex = listState.firstVisibleItemIndex
                val offset = abs(index - centerIndex)

                val scale = 1f - (offset * 0.15f).coerceAtMost(0.3f)
                val alpha = 1f - (offset * 0.3f).coerceAtMost(0.6f)

                VinylCarouselItem(
                    vinyl = vinyl,
                    scale = scale,
                    alpha = alpha
                )
            }
        }
    }
}

@Composable
fun VinylCarouselItem(
    vinyl: Vinyl,
    scale: Float,
    alpha: Float
) {
    Card(
        modifier = Modifier
            .size(220.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Image(
            painter = painterResource(id = vinyl.imageRes),
            contentDescription = vinyl.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
