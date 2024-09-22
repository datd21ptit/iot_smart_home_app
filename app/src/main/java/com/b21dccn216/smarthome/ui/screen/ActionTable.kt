package com.b21dccn216.smarthome.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.SmartHomeViewmodel
import com.b21dccn216.smarthome.model.TableResponse
import com.b21dccn216.smarthome.ui.components.FilterButtonWithDialog
import com.b21dccn216.smarthome.ui.components.FilterChip
import com.b21dccn216.smarthome.ui.components.PageButton

@Composable
fun ActionTableScreen(
    viewmodel: SmartHomeViewmodel,
    titleColumn: List<String>,
    innerPadding: PaddingValues,
    tableData: TableResponse
) {
    val uiState by viewmodel.uiStateTable.collectAsState()
    Column(
        modifier = Modifier
            .padding(top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 8.dp, end = 8.dp
            )
    ){
        var selectedFilters by remember{ mutableStateOf(uiState.row) }
        var selectedDate by remember{ mutableStateOf(uiState.time) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            FilterButtonWithDialog(
                selectedFilters = selectedFilters,
                selectedDate = selectedDate,
                titleColumn = titleColumn,
                onvalueChange = { row, value ->
                    val updateList = selectedFilters.toMutableList()
                    updateList[row] = value
                    selectedFilters = updateList
                },
                onClearALl = {
                    selectedFilters = listOf("", "", "")
                    selectedDate = ""
                    viewmodel.addFilter(uiState.copy(row = listOf("", "", ""), time = ""))
                },
                onConfirmClick = {
                    viewmodel.addFilter(uiState.copy(row = selectedFilters, time = selectedDate))
                },
                onDateChange = {
                    selectedDate = it
                    viewmodel.addFilter(uiState.copy(time = it))
                }
            )
            LazyRow {
                uiState.row.forEachIndexed { index, value ->
                    if(value.isNotEmpty()){
                        item {
                            FilterChip(text = titleColumn[index] + " = " + value,
                                onClose = {
                                    val updateList = uiState.row.toMutableList()
                                    updateList[index] = ""
                                    viewmodel.addFilter(uiState.copy(row = updateList ))
                                    selectedFilters = updateList
                                }
                            )
                        }
                    }
                }
                if(uiState.time.isNotEmpty()){
                    item{
                        FilterChip(text = "Time: " + uiState.time, onClose = {
                            viewmodel.addFilter(uiState.copy(time = ""))
                            selectedDate = ""
                        }
                        )
                    }
                }
            } // Filter Chip
        }
//        Title Row
//        TitleRow(titleColumn, onClickCell = {})
        LazyColumn(Modifier.fillMaxSize()) {
//            Rows
            items(tableData.data) {
                TableRow(it = it)
            }
//            Page button
            item{
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    (1..tableData.totalPages).forEach{ index ->
                        item {
                            PageButton(
                                onPageSelected = {viewmodel.addFilter(uiState.copy(page = index.toString()))},
                                isPageDisplay = tableData.page == index,
                                page = index
                            )
                        }
                    }
                }
            }
        }
    }
}
