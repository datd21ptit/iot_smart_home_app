package com.b21dccn216.smarthome.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.R
import com.b21dccn216.smarthome.ui.components.ActionBox
import com.b21dccn216.smarthome.ui.components.ChartData
import com.b21dccn216.smarthome.ui.components.LineChartComponent
import com.b21dccn216.smarthome.ui.components.SensorInformationBox

val chartData = listOf(
    ChartData("T", 30),
    ChartData("W", 25),
    ChartData("F", 30),
    ChartData("SA", 20),
    ChartData("SU", 22),
    ChartData("M", 13),
)
@Composable
fun DashboardScreen(
    innerPadding: PaddingValues
){
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding(), bottom = 8.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(key = 0) { SensorInformationBox(icon = R.drawable.thermometer,
                name = "T", unit = R.drawable.celsius, value = "25", color = Color(0xFFFF8343)) }
            item(key = 6) { LineChartComponent(name = "Temparature", chartData = chartData) }

            item(key = 1) { SensorInformationBox(icon = R.drawable.humidity,
                name = "T", unit = R.drawable.percent, value = "25", color = Color(0xFF179BAE)) }

            item(key = 7) { LineChartComponent(name = "Humidity", chartData = chartData, colorChart = Color(0xFF179BAE)) }
            item(key = 2) { SensorInformationBox(icon = R.drawable.brightness,
                name = "T", value = "100", color = Color(0xFFFFAD60)) }
            item(key = 3) { ActionBox(icon = R.drawable.lightbulb, deviceName = "Light Bulb") }
            item(key = 4) { ActionBox(icon = R.drawable.fan, deviceName = "Fan") }
            item(key = 5) { ActionBox(icon = R.drawable.fan, deviceName = "Fan") }
        }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview(){
    DashboardScreen(PaddingValues())
}