package com.b21dccn216.smarthome.network

import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.TableResponse
import com.b21dccn216.smarthome.model.TableUiState
import retrofit2.Response

class SmartHomeRepository(
    private val apiService: ApiService
) {
    suspend fun getDashboardUiData(limit: Int): DashboarUiState = apiService.getDashboardData(limit)

    suspend fun sendAction(device: String, state: String): Response<Void>{
        return apiService.sendAction(device, state)
    }

    suspend fun getSensorDataTable(
        uiState: TableUiState
    ): TableResponse = apiService.getSensorTable(uiState.page, uiState.row[0], uiState.row[1], uiState.row[2], uiState.time)

    suspend fun getActionDataTable(
        uiState: TableUiState
    ): TableResponse = apiService.getActionTable(uiState.page, uiState.row[0], uiState.row[1], uiState.row[2], uiState.time)
}