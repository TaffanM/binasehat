package com.mage.binasehat.ui.screen.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthEditText(
    value: String,
    onValueChange: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.tanggal_lahir),
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable(onClick = { Log.d("BirthEditText", "OutlinedTextField clicked")
                    openDialog = true }),
            textStyle = Typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            placeholder = {
                Text(
                    stringResource(R.string.tanggal_lahir),
                    color = onSurfaceColor.copy(alpha = 0.5f),
                    style = Typography.bodyMedium
                )
            },
            readOnly = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(R.color.gray_300),
                cursorColor = colorResource(R.color.gray_300),
            ),
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.pilih_tanggal),
                    modifier = Modifier.clickable {
                        openDialog = true
                    }
                )
            },
            enabled = false
        )
        if (openDialog) {
            DatePickerDialog(
                modifier = Modifier
                    .padding(horizontal = 32.dp),
                onDismissRequest = { openDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Date(millis)
                                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                onValueChange(formatter.format(date))
                            }
                            openDialog = false
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorResource(R.color.green_primary)
                        )
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorResource(R.color.green_primary)
                        ),
                    ) {
                        Text(stringResource(R.string.batal))
                    }
                },
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedYearContentColor = MaterialTheme.colorScheme.surface,
                        selectedDayContentColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}