package com.b21dccn216.smarthome.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.SmartHomeViewmodel
import com.b21dccn216.smarthome.R
import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.SensorType
import com.b21dccn216.smarthome.ui.components.ActionBox
import com.b21dccn216.smarthome.ui.components.LineChartComponent
import com.b21dccn216.smarthome.ui.components.SensorInformationBox


@Composable
fun DashboardScreen(
    viewmodel: SmartHomeViewmodel,
    innerPadding: PaddingValues
){
    val uiState by viewmodel.uiStateDashboard.collectAsState()
    val options = listOf(500, 1000, 10000, 50000)
    LazyVerticalGrid(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = innerPadding.calculateBottomPadding()),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = innerPadding.calculateTopPadding(), bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { SensorInformationBox( sensorType = SensorType.Temperature, value = uiState.listTemp.last().toString() ) }
        item { SensorInformationBox( sensorType = SensorType.Humidity, value = uiState.listHumid.last().toString() ) }
        item { SensorInformationBox( sensorType = SensorType.Light, value = uiState.listLight.last().toString() ) }
        Log.d("retrofitD", "CLICK2")
        item {
            ActionBox(icon = R.drawable.lightbulb, deviceName = "Light Bulb",
                isOn = uiState.led == 1,
                onClick = {
                    viewmodel.clickAction(
                        DashboarUiState(
                            led = 1-uiState.led,
                            fan = uiState.fan,
                            relay = uiState.relay))
                }
            )
        }
        item {
            ActionBox(icon = R.drawable.fan, deviceName = "Fan",
                isOn = uiState.fan == 1,
                onClick = {
                    viewmodel. clickAction(DashboarUiState(
                        led = uiState.led,
                        fan = 1- uiState.fan,
                        relay = uiState.relay))
                }
            )
        }
        item {
            ActionBox(icon = R.drawable.fan, deviceName = "Relay",
                isOn = uiState.relay == 1,
                onClick = {
                    Log.d("retrofitD", "CLICK")
                    viewmodel.clickAction(DashboarUiState(
                        led = uiState.led,
                        fan = uiState.fan,
                        relay = 1-uiState.relay))
                }) }
        item( span = { GridItemSpan(2) }) {
            LineChartComponent(
                name = "Light",
                chartData = uiState.listLight,
                step = 200,
            ) }
        item( span = { GridItemSpan(2) }) {
            LineChartComponent(
                name = "Humidity",
                chartData = uiState.listHumid,
                step = 10,
                colorChart = Color(0xFF179BAE),
            )
        }
        item( span = { GridItemSpan(2) }) {
            LineChartComponent(
                name = "Temparature",
                step = 10,
                chartData = uiState.listTemp,
            ) }


    }
}

