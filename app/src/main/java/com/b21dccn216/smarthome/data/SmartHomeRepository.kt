package com.b21dccn216.smarthome.data

import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.TableResponse
import com.b21dccn216.smarthome.model.TableUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Query

class SmartHomeRepository(
    private val apiService: ApiService
) {
    suspend fun getSensorDataFlow(): Flow<List<DashboarUiState>> = flow{
        while (true){
            emit(apiService.getSensorData())
            delay(2000)
        }
    }

    suspend fun getSensorData(): List<DashboarUiState> = apiService.getSensorData()

    suspend fun senAction(led: Int, fan: Int, relay: Int): Response<Void>{
        return apiService.sendAction(led.toString(),relay.toString(), fan.toString())
    }

    suspend fun getChartData(type: String): List<Int>{
        return apiService.getChartData(type)
    }

    suspend fun getSensorDataTable(
        uiState: TableUiState
    ): TableResponse = apiService.getSensorTable(uiState.page, uiState.row1, uiState.row2, uiState.row3, uiState.time)

    suspend fun getActionDataTable(
        uiState: TableUiState
    ): TableResponse = apiService.getActionTable(uiState.page, uiState.row1, uiState.row2, uiState.row3, uiState.time)
}