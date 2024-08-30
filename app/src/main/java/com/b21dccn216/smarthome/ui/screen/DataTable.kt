package com.b21dccn216.smarthome.ui.screen

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.SmartHomeViewmodel
import com.b21dccn216.smarthome.items
import com.b21dccn216.smarthome.ui.components.BottomNavigationApp
import com.b21dccn216.smarthome.ui.components.FilterButtonWithDialog
import com.b21dccn216.smarthome.ui.components.FilterChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(
    modifier: Modifier,
    viewmodel: SmartHomeViewmodel,
    titleColmn: List<String>,
    title: String,
    selectedIndex: Int,
    onClickNavItem: (String) -> Unit,
) {
    val uiState by viewmodel.uiStateTable.collectAsState()
    val tableData = uiState.tableData
    val column1Weight = .4f
    val column2Weight = .7f
    val column3Weight = .3f
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = title) })
        },
        bottomBar = {
            BottomNavigationApp(
                onClickNavItem = { onClickNavItem(it) },
                currentIndex = selectedIndex
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 8.dp, end = 8.dp)
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
                    onvalueChange = { row, value ->
                        selectedFilters = selectedFilters.mapIndexed { index, it ->
                            if (index == row) value else it
                        }
                    },
                    onClearALl = {
                        selectedFilters = listOf("", "", "")
                        selectedDate = ""
                        viewmodel.addFilter(uiState.copy(row = listOf("", "", ""), time = ""))
                    },
                    onConfirmClick = {
                        viewmodel.addFilter(viewmodel.uiStateTable.value.copy(row = selectedFilters, time = selectedDate))
                    },
                    onDateChange = {
                        selectedDate = it
                        viewmodel.addFilter(uiState.copy(it))}
                )

                LazyRow {
                    uiState.row.forEachIndexed { index, value ->
                        if(value.isNotEmpty()){
                            item {
                                FilterChip(text = titleColmn[index] + " = " + value,
                                    onClose = {
                                        viewmodel.addFilter(uiState.copy(row = getList(index, uiState.row) ))
                                        selectedFilters = getList(index, uiState.row)
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

                }
            }

            Row(
                modifier = Modifier.background(color =Color(0xFF179BAE),
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            ) {
                TableCell(text = "id", weight = column1Weight,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(topStart = 15.dp))
                )
                TableCell(text = titleColmn[0], weight = column3Weight)
                TableCell(text = titleColmn[1], weight = column3Weight)
                TableCell(text = titleColmn[2], weight = column3Weight)
                TableCell(text = "Time", weight = column2Weight,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(topEnd = 15.dp))
                )
            }

            LazyColumn(Modifier.fillMaxSize()) {
                items(tableData.data) {
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = it[0], weight = column1Weight)
                        TableCell(text = it[1], weight = column3Weight)
                        TableCell(text = it[2], weight = column3Weight)
                        TableCell(text = it[3], weight = column3Weight)
                        TableCell(text = it[4], weight = column2Weight)
                    }
                }
                item{
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        (1..tableData.totalPages).forEach{ index ->
                            item(){
                                PageButton(
                                    onPageSelected = {viewmodel.addFilter(uiState.copy(page = it.toString()))},
                                    index = index,
                                    pageIndex = tableData.page
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}


fun getList(index: Int, listS: List<String>): List<String> {
    var list = listS.toMutableList()
    if(index in list.indices){
        list[index] = ""
    }
    Log.e("datatable", list.toString())
    return list
}
@Composable
fun PageButton(
    onPageSelected: (Int) -> Unit,
    index: Int,
    pageIndex: Int
){
    Button(
        onClick = { onPageSelected(index) },
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
            color = if(pageIndex == index) Color.Black else Color.Transparent
        )
    ) {
        Text(text = index.toString())
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
