package com.b21dccn216.smarthome.model

data class TableUiState(
    val id: String = "",
    val row: List<String> = listOf("", "", ""),
    val time: String = "",
    val page: String = "1",
//
    val tableSensorData:TableResponse = TableResponse(
        data = listOf(listOf("id", "row1", "row2", "row3", "time")),
        page = 1,
        totalRows = 100,
        totalPages = 10
    ),
    val tableActionData:TableResponse= TableResponse(
        data = listOf(listOf("id", "row1", "row2", "row3", "time")),
        page = 1,
        totalRows = 100,
        totalPages = 10
    ),
)



data class TableResponse(
    val page: Int,
    val totalPages: Int,
    val totalRows: Int,
    val data: List<List<String>>
)