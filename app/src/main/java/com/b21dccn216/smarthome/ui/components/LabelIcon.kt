package com.b21dccn216.smarthome.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun LabelIcon(
    modifier: Modifier,
    color: Color = Color.Transparent,
    isOn: Boolean = true,
    value: String? = null,
    @DrawableRes icon: Int)
{
    var mod = modifier.size(64.dp).padding(8.dp)
    if(value != null){
        mod = mod.background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFD9D9D9), color),
                startY = (100 - (value.toInt() - 10)*9/4).toFloat()),
            shape = CircleShape)
    }else{
        mod = mod.background(color = Color(0xFFD9D9D9),
            shape = CircleShape
        )
    }
    Box(
        modifier = mod
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = if(isOn) Color.Black else Color.Black.copy(alpha = 0.3f),
            modifier = Modifier
                .padding(8.dp)
        )
    }
}