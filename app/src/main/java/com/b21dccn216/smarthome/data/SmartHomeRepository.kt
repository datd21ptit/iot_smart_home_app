package com.b21dccn216.smarthome.data

import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.TableResponse
import com.b21dccn216.smarthome.model.TableUiState
import retrofit2.Response

class SmartHomeRepository(
    private val apiService: ApiService
) {
    suspend fun getSensorData(): List<DashboarUiState> = apiService.getSensorData()

    suspend fun sendAction(led: Int, fan: Int, relay: Int): Response<Void>{
        return apiService.sendAction(led.toString(),relay.toString(), fan.toString())
    }

    suspend fun getSensorDataTable(
        uiState: TableUiState
    ): TableResponse = apiService.getSensorTable(uiState.page, uiState.row[0], uiState.row[1], uiState.row[2], uiState.time)

    suspend fun getActionDataTable(
        uiState: TableUiState
    ): TableResponse = apiService.getActionTable(uiState.page, uiState.row[0], uiState.row[1], uiState.row[2], uiState.time)
}