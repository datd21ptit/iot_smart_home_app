package com.b21dccn216.smarthome.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SensorInformationBox(
    @DrawableRes icon: Int,
    name: String,
    @DrawableRes unit: Int = -1,
    color: Color,
    value: String
){
    ConstraintLayout(
        modifier = Modifier
            .size(width = 165.dp, 100.dp)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(15))
            .clip(RoundedCornerShape(percent = 15))
            .background(color = Color.White)
    ){
        val(sensorIcon, valueText, unitIcon) = createRefs()
        LabelIcon(
            icon = icon,
            color = color,
            modifier = Modifier
            .constrainAs(sensorIcon){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 8.dp)
            },
            value = value
        )
        if(unit != -1){
            Icon(
                painter = painterResource(id = unit),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(unitIcon) {
                        end.linkTo(parent.end, margin = 16.dp)
//                top.linkTo(valueText.top)
                        bottom.linkTo(valueText.bottom, margin = 8.dp)
                    })
        }
        Text(
            text = value,
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 40.sp,
            ),
            modifier = Modifier.constrainAs(valueText){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                if(unit != -1) end.linkTo(unitIcon.start, margin = 0.dp) else end.linkTo(parent.end, margin = 16.dp)
            }
        )
    }
}


@Preview( showBackground = true)
@Composable
fun PreviewSensorInformationBox(){

    Row(
        modifier = Modifier.padding(25.dp).fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween){
        SensorInformationBox(
            icon = R.drawable.thermometer,
            name = "Temperature",
            color = Color.Red,
            value = "30",
            unit = R.drawable.celsius)

        SensorInformationBox(
            icon = R.drawable.humidity,
            color = Color.Cyan,
            name = "Temperature",
            value = "10",
            unit = R.drawable.percent)
    }

}

