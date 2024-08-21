package com.b21dccn216.smarthome.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.b21dccn216.smarthome.items
import com.b21dccn216.smarthome.ui.components.DatePickerDocked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableScreen(
    modifier: Modifier,
    tableData: List<SensorData>,
    title: String,
    selectedIndex: Int,
    onClick: (Int, String) -> Unit) {

    val column1Weight = .4f
    val column2Weight = .7f
//    var selectedIndex by remember{ mutableStateOf(1) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = title, style = MaterialTheme.typography.titleMedium) })
        },
        bottomBar = {
            NavigationBar() {
                items.forEachIndexed{ index, item ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            onClick(index, item.title)
                        },
                        icon = {
                            Icon(imageVector = if(index == selectedIndex) item.selectedIon else item.unselectedIcon,
                                contentDescription = null)
                        })
                }
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding(), start = 8.dp, end = 8.dp)
        ){
            DatePickerDocked()
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Id", weight = column1Weight)
                TableCell(text = "Temp", weight = column2Weight)
                TableCell(text = "Humid", weight = column2Weight)
                TableCell(text = "Light", weight = column2Weight)
                TableCell(text = "Time", weight = column2Weight)
            }

            LazyColumn(Modifier.fillMaxSize()) {
                items(tableData) { it->
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = it.id.toString(), weight = column1Weight)
                        TableCell(text = it.temp.toString(), weight = column2Weight)
                        TableCell(text = it.humid.toString(), weight = column2Weight)
                        TableCell(text = it.light.toString(), weight = column2Weight)
                        TableCell(text = it.time.toString(), weight = column2Weight)
                    }
                }
            }
        }
    }
}
data class SensorData(
    val id: Int,
    val temp: Int,
    val humid: Int,
    val light: Int,
    val time: String
)

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}
