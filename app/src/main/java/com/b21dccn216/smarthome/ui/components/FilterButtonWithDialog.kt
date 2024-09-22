package com.b21dccn216.smarthome.ui.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FilterButtonWithDialog(
    selectedFilters: List<String>,
    titleColumn: List<String>,
    onClearALl: () -> Unit,
    onvalueChange: (Int, String) -> Unit,
    onConfirmClick: () -> Unit,
    selectedDate: String,
    onDateChange: (String) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(
            false
        )
    }
    Button(
        modifier = Modifier
            .padding(12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(50)),
        shape = RoundedCornerShape(50),
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = Color.Black
        ),
        onClick = { showDialog = true
                  Log.d("datatable", selectedFilters.toString())},
        contentPadding = PaddingValues()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text("Filter")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Filled.Add, contentDescription = "Filter", modifier = Modifier.height(16.dp))
        }
    }

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text("Filter Options") },
            text = {
                FillterDialog(
                    titleColumn = titleColumn,
                    onClearALl = {
                        onClearALl()
                        showDialog = false },
                    onvalueChange = { row, value ->
                        onvalueChange(row, value)
                    },
                    selectedFilters = selectedFilters,
                    selectedDate = selectedDate,
                    onDateSelected = { onDateChange(it) },
                    onDeselected = { onDateChange("")}
                )
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onConfirmClick()
                }) {
                    Text("Apply")
                }
            }
        )
    }
}


@Composable
fun FillterDialog(
    titleColumn: List<String>,
    onClearALl: () -> Unit,
    selectedFilters: List<String>,
    onvalueChange: (Int, String) -> Unit,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    onDeselected: () -> Unit,

){
    Column {
        Text(
            "Clear All",
            color = Color.Red.copy(alpha = 0.5f),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable {
                    onClearALl()
                })
        HorizontalDivider()
        // Filter options for rows
        for (row in 0..2) {
            OutlinedTextField(
                label = { Text(titleColumn[row]) },
                value = selectedFilters[row], onValueChange = {
                    onvalueChange(row, it)
                },
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
        DatePickerDocked(
            value = selectedDate,
            onDateSelected = {onDateSelected(it)},
            onDeselected = {onDeselected()},
            modifier = Modifier
        )
        HorizontalDivider()
        // Date picker

    }
}


@Preview(showBackground = true)
@Composable
fun Preis(){
    Row (modifier = Modifier.padding(16.dp)){
        FilterButtonWithDialog(
            selectedFilters = listOf("", ""),
            onvalueChange = {_,_ ->},
            onConfirmClick = {},
            onDateChange = {},
            onClearALl = {},
            selectedDate = "Date",
            titleColumn = listOf("Column1", "Column2", "Column3"),
        )
    }

}