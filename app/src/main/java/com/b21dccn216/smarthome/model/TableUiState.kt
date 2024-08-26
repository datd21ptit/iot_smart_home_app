package com.b21dccn216.smarthome.model

data class TableUiState(
//    User input to search
    val id: String = "",
    val row1: String = "",
    val row2: String = "",
    val row3: String = "",
    val time: String = "",
    val page: String = "1",
//
    val tableData:TableResponse
)



data class TableResponse(
    val page: Int,
    val totalPages: Int,
    val totalRows: Int,
    val data: List<List<String>>
)