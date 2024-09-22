package com.b21dccn216.smarthome.model

import androidx.compose.ui.graphics.Color
import com.b21dccn216.smarthome.R


data class DashboarUiState(

//    val listDevice: List<DeviceType> = listOf(DeviceType.LighBulb, DeviceType.Fan, DeviceType.Relay),

    val led: String = "off",
    val fan: String = "off",
    val relay: String = "off",

    val listTemp: MutableList<Int> = mutableListOf(),
    val listHumid: MutableList<Int> = mutableListOf(),
    val listLight: MutableList<Int> = mutableListOf()
)

sealed class DeviceType(
    val icon: Int,
    val name: String,
    val state: String,
){
    data object LighBulb : DeviceType(
        icon = R.drawable.lightbulb,
        name = "led",
        state = "off"
    )
    data object Fan : DeviceType(
        icon = R.drawable.fan,
        name = "fan",
        state = "off"
    )
    data object Relay : DeviceType(
        icon = R.drawable.fan,
        name = "relay",
        state = "off"
    )
}

sealed class SensorType(
    val icon: Int,
    val unit: Int,
    val color: Color,

) {
    // value = 100 - (value - minValue) / (maxValue - minValue) * 100
    abstract fun caculateGradient(value: Int): Int

    data object Temperature : SensorType(
        icon = R.drawable.thermometer,
        unit = R.drawable.celsius,
        color = Color(0xFFFF8343)
    ){
        override fun caculateGradient(value: Int): Int {
            return (100 - 2 * value)
        }
    }
    data object Humidity : SensorType(
        icon = R.drawable.humidity,
        unit = R.drawable.percent,
        color = Color(0xFF179BAE)
    ) {
        override fun caculateGradient(value: Int): Int {
            return (100 - value)
        }
    }

    data object Light : SensorType(
        icon = R.drawable.brightness,
        unit = R.drawable.lux,
        color = Color(0xFFFF8343)
    ) {
        override fun caculateGradient(value: Int): Int {
            return (100 - (value/15))
        }
    }
}