package com.b21dccn216.smarthome.ui.screen

import android.widget.DatePicker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import kotlin.text.format
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun FilterButtonWithDialog() {
    var showDialog by remember {
        mutableStateOf(
            false
        )
    }
    var selectedFilters by remember {
        mutableStateOf(
            listOf<String>("", "", "", "")
        )
    }

    Button(onClick = { showDialog = true }) {
        Text("Filter")
    }

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text("Filter Options") },
            text = {
                Column {
                    Text(
                        "Clear All",
                        modifier = Modifier.clickable {
                            selectedFilters = listOf<String>("", "", "", "")
                            showDialog = false
                        })
                    Divider()
                    // Filter options for rows
                    for (row in 0..2) {
                        OutlinedTextField(
                            value = selectedFilters[row], onValueChange = {
                                selectedFilters = selectedFilters.mapIndexed { index, filter ->
                                    if (index == row) it else filter
                                }
                            }
                        )
                    }
                    Divider()
                    // Date picker

                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Apply")
                }
            }
        )
    }

    // Use selectedFilters and selectedDate to apply filtering logic
    // ...
}
