package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyStats() {
    var height by remember { mutableStateOf<Int?>(null) }
    var weight by remember { mutableStateOf<Int?>(null) }

    var showHeightPicker by remember { mutableStateOf(false) }
    var showWeightPicker by remember { mutableStateOf(false) }
    var tempHeight by remember { mutableIntStateOf(175) }
    var tempWeight by remember { mutableIntStateOf(60) }

    LaunchedEffect(showHeightPicker) {
        if (!showHeightPicker) {
            tempHeight = height ?: 175
        }
    }

    LaunchedEffect(showWeightPicker) {
        if (!showWeightPicker) {
            tempWeight = weight ?: 60
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.statistik_badan),
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            HealthForm(
                onClick = { showHeightPicker = true},
                input = height?.toString() ?: "",
                placeholder = stringResource(R.string.tinggi),
                icon = painterResource(R.drawable.rounded_height_24),
                trailingText = "cm",
                modifier = Modifier
                    .weight(1f)
            )
            HealthForm(
                onClick = { showWeightPicker = true},
                input = weight?.toString() ?: "",
                placeholder = stringResource(R.string.berat),
                icon = painterResource(R.drawable.rounded_monitor_weight_24),
                trailingText = "kg",
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (showHeightPicker) {
        ModalBottomSheet(
            onDismissRequest = {
                showHeightPicker = false
            },
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeightPicker(
                    initialHeight = tempHeight,
                    onHeightChange = { newHeight ->
                        tempHeight = newHeight
                    }
                )

                CustomOutlinedButton(
                    text = stringResource(R.string.simpan),
                    onClick = {
                        height = tempHeight
                        showHeightPicker = false
                    }
                )

            }
        }
    }

    if (showWeightPicker) {
        ModalBottomSheet(
            onDismissRequest = {
                showWeightPicker = false
            },
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeightPicker(
                    initialWeight = tempWeight,
                    onWeightChange = { newWeight ->
                        tempWeight = newWeight
                    }
                )

                CustomOutlinedButton(
                    text = stringResource(R.string.simpan),
                    onClick = {
                        weight = tempWeight
                        showWeightPicker = false
                    }
                )

            }
        }
    }
}

@Composable
fun HealthForm(
    onClick: () -> Unit,
    input: String,
    placeholder: String,
    icon: Painter,
    trailingText: String,
    modifier: Modifier = Modifier
) {
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    OutlinedTextField(
        onValueChange = { },
        value = input,
        placeholder = {
            Text(
                placeholder,
                color = onSurfaceColor.copy(alpha = 0.5f),
                style = Typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colorResource(R.color.gray_300),
            cursorColor = colorResource(R.color.gray_300),
        ),
        leadingIcon = {
            Icon(
                painter = icon, contentDescription = "",
            )
        },
        trailingIcon = {
            Text(
                trailingText,
                style = Typography.bodyMedium,
            )
        },
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        textStyle = Typography.bodyMedium.copy(color = onSurfaceColor),
        enabled = false,
        singleLine = true,
    )
}