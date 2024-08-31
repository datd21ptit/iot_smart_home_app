package com.b21dccn216.smarthome


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b21dccn216.smarthome.model.Destinations.DASHBOARD
import com.b21dccn216.smarthome.model.Destinations.SENSOR_DATA_TABLE
import com.b21dccn216.smarthome.data.SmartHomeRepository
import com.b21dccn216.smarthome.model.AppState.LOADED
import com.b21dccn216.smarthome.model.AppState.LOADING
import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.Destinations.ACTION_DATA_TABLE
import com.b21dccn216.smarthome.model.TableResponse
import com.b21dccn216.smarthome.model.TableUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SmartHomeViewmodel(
    private val repository: SmartHomeRepository
) : ViewModel(){

    private var _uiStateDashboard = MutableStateFlow(DashboarUiState())
    val uiStateDashboard: StateFlow<DashboarUiState> = _uiStateDashboard.asStateFlow()

    private val _appState = MutableStateFlow(LOADING)
    val appState = _appState.asStateFlow()

    private val _uiStateTable = MutableStateFlow(TableUiState())
    val uiStateTable: StateFlow<TableUiState> = _uiStateTable.asStateFlow()


    private var _currentScreen = MutableStateFlow(DASHBOARD)
    val currentScreen = _currentScreen.asStateFlow()
//    INIT
    init {
        viewModelScope.launch {
            _currentScreen.collectLatest{ current ->
                when(current){
                    DASHBOARD -> {
                        while(current == DASHBOARD){
                            getDashboardData()
                            delay(2000)
                        }
                    }
                    SENSOR_DATA_TABLE -> {
                        while (current == SENSOR_DATA_TABLE){
                            getTableData()
                            delay(2000)
                        }
                    }
                    ACTION_DATA_TABLE -> {
                        while (current == ACTION_DATA_TABLE){
                            getTableDataAction()
                            delay(2000)
                        }
                    }

                }
            }
        }
    }

    private suspend fun getTableData(){
        try{
            val result = repository.getSensorDataTable(_uiStateTable.value)
            _uiStateTable.update { value ->
                value.copy(
                    tableSensorData = result
                )
            }
            _appState.value = LOADED
            Log.d("viewmodel", "get sensor data table")
        }catch (e: Exception){
            Log.e("viewmodel", e.toString())
        }
    }

    private suspend fun getTableDataAction(){
        try{
            val result = repository.getActionDataTable(_uiStateTable.value)
            _uiStateTable.update { value ->
                value.copy(
                    tableActionData = result
                )
            }
            _appState.value = LOADED
            Log.d("viewmodel", "get action table")
        }catch (e: Exception){
            Log.e("viewmodel", e.toString())
        }
    }

    private suspend fun getDashboardData(){
        try {
            val listResult = repository.getSensorData()[0]
            _uiStateDashboard.update {
                listResult
            }
            Log.d("viewmodel", listResult.toString())
//            getChartData()
            _appState.value = LOADED
        }catch (e: Exception){
            Log.e("viewmodel", e.toString())
        }
    }

//    private suspend fun getChartData(){
//        try {
//            val listTemp = repository.getChartData("temp").toMutableList()
//            listTemp.removeIf{ it == 0}
//            _uiStateDashboard.update { newValue ->
//                newValue.copy(listTemp = listTemp,
//                    temp = listTemp[listTemp.lastIndex])
//            }
//            val listHumid = repository.getChartData("humid").toMutableList()
//            listHumid.removeIf{ it == 0}
//            _uiStateDashboard.update { newValue ->
//                newValue.copy(listHumid = listHumid,
//                    humid = listHumid[listHumid.lastIndex])
//            }
//            val listLight = repository.getChartData("light").toMutableList()
//            _uiStateDashboard.update { newValue ->
//                newValue.copy(listLight = listLight,
//                    light = listLight[listLight.lastIndex])
//            }
//            Log.e("viewmodel", "get Chart data")
//        }catch (e: Exception){
//            Log.e("viewmodel", e.toString())
//        }
//
//    }


    fun clickAction(state: DashboarUiState){
        viewModelScope.launch {
            try {
                val response = repository.senAction(led = state.led, fan = state.fan, relay = state.relay)
                Log.d("viewmodel", response.toString())
                if(response.isSuccessful && response.code() == 200){
                    _uiStateDashboard.update { value ->
                        value.copy(
                            led = state.led,
                            fan = state.fan,
                            relay = state.relay
                        )
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun navigateTo(screen: Pair<Int, String>){
        _currentScreen.value = screen
        _uiStateTable.update { value ->
            value.copy(
                page = "1",
                row = listOf("", "", ""),
                time = "",
            )
        }
    }

    fun addFilter(state: TableUiState){
        _uiStateTable.update { value ->
            value.copy(
                page = state.page,
                row = state.row,
                time = state.time
            )
        }
    }

}


