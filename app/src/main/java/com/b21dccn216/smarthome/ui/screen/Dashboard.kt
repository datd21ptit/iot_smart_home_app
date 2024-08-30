package com.b21dccn216.smarthome.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.SmartHomeViewmodel
import com.b21dccn216.smarthome.R
import com.b21dccn216.smarthome.data.ApiService
import com.b21dccn216.smarthome.data.SmartHomeRepository
import com.b21dccn216.smarthome.data.retrofit
import com.b21dccn216.smarthome.items
import com.b21dccn216.smarthome.model.AppState.LOADING
import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.SensorType
import com.b21dccn216.smarthome.ui.components.ActionBox
import com.b21dccn216.smarthome.ui.components.BottomNavigationApp
import com.b21dccn216.smarthome.ui.components.ChartData
import com.b21dccn216.smarthome.ui.components.LineChartComponent
import com.b21dccn216.smarthome.ui.components.SensorInformationBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewmodel: SmartHomeViewmodel,
    onClickNavItem: (String)->Unit
){
    val appState = viewmodel.appState.collectAsState()
    val uiState by viewmodel.uiStateDashboard.collectAsState()
    if(appState.value == LOADING){
        LoadingScreen()
    }else{
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Dashboard") })
        },
        bottomBar = {
            BottomNavigationApp(
                onClickNavItem = { onClickNavItem(it) },
                currentIndex = 0,
            )
        }
    ){ innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = innerPadding.calculateBottomPadding()),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding(), bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { SensorInformationBox( sensorType = SensorType.Temperature, value = uiState.temp.toString() ) }
            item { SensorInformationBox( sensorType = SensorType.Humidity, value = uiState.humid.toString() ) }
            item { SensorInformationBox( sensorType = SensorType.Light, value = uiState.light.toString() ) }
            Log.d("retrofitD", "CLICK2")
            item {
                ActionBox(icon = R.drawable.lightbulb, deviceName = "Light Bulb",
                    isOn = uiState.led == 1,
                    onClick = {
                        viewmodel.clickAction(DashboarUiState(0,0,0,
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
                        viewmodel. clickAction(DashboarUiState(0,0,0,
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
                        viewmodel.clickAction(DashboarUiState(0,0,0,
                            led = uiState.led,
                            fan = uiState.fan,
                            relay = 1-uiState.relay))
                    }) }
            item( span = { GridItemSpan(2) }) {
                LineChartComponent(
                    name = "Light",
                    chartData = uiState.listLight,
                    step = 200,) }
            item( span = { GridItemSpan(2) }) {
                LineChartComponent(
                    name = "Temparature",
                    step = 1,
                    chartData = uiState.listTemp) }
            item( span = { GridItemSpan(2) }) {
                LineChartComponent(
                    name = "Humidity",
                    chartData = uiState.listHumid,
                    step = 1,
                    colorChart = Color(0xFF179BAE)) }

        }
    }

    }

}

