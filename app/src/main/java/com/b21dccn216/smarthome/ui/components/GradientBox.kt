package com.b21dccn216.smarthome.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp

@Preview(showBackground = true)
@Composable
fun GradientBox() {
    val value: Float = 0.1f
    val clampedValue = value.coerceIn(0f, 100f) / 100f // Clamp the value between 0 and 1
    val startColor = Color.Red.copy(alpha = clampedValue)
    val endColor = Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(startColor, endColor),
                    startY = 100f,
//                    endY = 1000f // Adjust this value based on the height of the Box
                )
            )
    ) {
        // Content of the Box
    }
}

