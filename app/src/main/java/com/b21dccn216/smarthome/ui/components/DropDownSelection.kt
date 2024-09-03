package com.b21dccn216.smarthome.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LimitOptionDropDown(
    onChange: (String) -> Unit = {},
    color: Color = Color.Black,
    selectedOption: String = "5",
) {


    Row(
        modifier = Modifier
            .width(100.dp) // Apply width to the entire dropdown box
            .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(15.dp))
            .padding(start = 8.dp)
        ,horizontalArrangement = Arrangement.SpaceBetween
        , verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedOption
        )
        Icon(
            Icons.Default.ArrowDropDown, contentDescription = null,
            modifier = Modifier
                .clickable {
                    onChange("")
                })
    }
}