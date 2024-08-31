package com.b21dccn216.smarthome.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.SmartHomeViewmodel
import com.b21dccn216.smarthome.model.TableResponse
import com.b21dccn216.smarthome.ui.components.FilterButtonWithDialog
import com.b21dccn216.smarthome.ui.components.FilterChip
//const val column1Weight = .4f
//const val column2Weight = .7f
//const val column3Weight = .3f

@Composable
fun TableScreen(
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
        TitleRow(titleColumn)
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

@Composable
fun TitleRow(
    titleColumn: List<String>
){
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(Color(0xFF179BAE))
    ) {
        TableCell(text = "Id", weight = .4f)
        TableCell(text = titleColumn[0], weight = .3f)
        TableCell(text = titleColumn[1], weight = .3f)
        TableCell(text = titleColumn[2], weight = .3f)
        TableCell(text = "Time", weight = .7f)
    }
}

@Composable
fun TableRow(
    it: List<String>
){
    Row(Modifier.fillMaxWidth()) {
        TableCell(text = it[0], weight = .4f)
        TableCell(text = it[1], weight = .3f)
        TableCell(text = it[2], weight = .3f)
        TableCell(text = it[3], weight = .3f)
        TableCell(text = it[4], weight = .7f)
    }
}

@Composable
fun PageButton(
    onPageSelected: () -> Unit,
    isPageDisplay: Boolean,
    page: Int
){
    Button(
        onClick = { onPageSelected() },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(5.dp))
            .size(height = 40.dp, width = 50.dp),
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Black),
        contentPadding = PaddingValues(),
        border = BorderStroke(
            width = 2.dp,
            color = if(isPageDisplay) Color.Black else Color.Transparent
        )
    ) {
        Text(text = page.toString())
    }
}

@Composable
fun RowScope.TableCell(
    modifier: Modifier = Modifier,
    text: String,
    weight: Float
) {
    val scrollState = rememberScrollState()
    Text(
        text = text,
        modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
            .horizontalScroll(scrollState),
        maxLines = 1,
        overflow = TextOverflow.Visible,
        textAlign = TextAlign.Center
    )
}
