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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.b21dccn216.smarthome.SmartHomeViewmodel
import com.b21dccn216.smarthome.model.TableUiState


@Composable
fun FilterButtonWithDialog(
    selectedFilters: List<String>,
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
            .clip(MaterialTheme.shapes.small)
            .padding(8.dp),
        colors = ButtonColors(
            containerColor = Color(0xFF179BAE),
            contentColor = androidx.compose.ui.graphics.Color.Black,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = androidx.compose.ui.graphics.Color.Black
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
                    onClearALl = {
                        onClearALl()
                        showDialog = false },
                    onvalueChange = { row, value ->
                        onvalueChange(row, value)
                    },
                    selectedFilters = selectedFilters,
                    selectedDate = selectedDate,
                    onDateSelected = {it -> onDateChange(it) },
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

    // Use selectedFilters and selectedDate to apply filtering logic
    // ...
}


@Composable
fun FillterDialog(
    onClearALl: () -> Unit,
    selectedFilters: List<String>,
    onvalueChange: (Int, String) -> Unit,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    onDeselected: () -> Unit
){
    Column {
        Text(
            "Clear All",
            modifier = Modifier.clickable {
                onClearALl()
            })
        Divider()
        // Filter options for rows
        for (row in 0..2) {
            OutlinedTextField(
                value = selectedFilters[row], onValueChange = { it ->
                    onvalueChange(row, it)
                },
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
        DatePickerDocked(
            value = selectedDate,
            onDateSelected = {onDateSelected(it)},
            onDeselected = {onDeselected()}
        )
        Divider()
        // Date picker

    }
}
