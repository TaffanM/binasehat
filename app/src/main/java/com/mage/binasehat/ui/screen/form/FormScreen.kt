package com.mage.binasehat.ui.screen.form

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.CustomOutlinedButton
import com.mage.binasehat.ui.screen.components.HeightPicker
import com.mage.binasehat.ui.screen.components.LoadingDialog
import com.mage.binasehat.ui.screen.components.SuccessDialog
import com.mage.binasehat.ui.screen.components.WeightPicker
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FormScreen(
    navController: NavHostController
) {
    var showLoadingDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
        ) {
            BackButton(
                text = stringResource(R.string.daftar),
                icon = painterResource(R.drawable.round_arrow_back_ios_24),
                onClick = {
                    navController.popBackStack()
                }
            )
            FormTitleText()
            BirthEditText()
            GenderOption()
            BodyStats()
            CustomFillButton(
                text = stringResource(R.string.kirim),
                onClick = {
                   showLoadingDialog = true
                }
            )
        }

        if (showLoadingDialog) {
            LoadingDialog(
                onDismiss = {
                    showLoadingDialog = false
                }
            )
        }

        if (showSuccessDialog) {
            SuccessDialog(
                onDismiss = {
                    showSuccessDialog = false
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                text = stringResource(R.string.sukses_daftar)
            )
        }
    }

    LaunchedEffect(showLoadingDialog) {
        if (showLoadingDialog) {
            delay(5000)
            showLoadingDialog = false
            showSuccessDialog = true

        }
    }
}

@Composable
fun FormTitleText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp),
            text = stringResource(R.string.form),
            fontSize = 24.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Harap isi informasi anda. Hal ini membantu kami menyesuaikan pengalaman anda menggunakan BinaSehat dan memastikan kami memiliki detail yang paling akurat",
            style = Typography.bodyMedium,
            textAlign = TextAlign.Start
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthEditText() {
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
            text = "Tanggal Lahir",
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
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
                            selectedDate = formatter.format(date)
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
                    Text("Batal")
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

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable {
                openDialog = true
            },
        textStyle = Typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        placeholder = {
            Text(
                "Tanggal Lahir",
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
                contentDescription = "Select date",
                modifier = Modifier.clickable {
                    openDialog = true
                }
            )
        }
    )
}

@Composable
fun GenderOption() {
    var selectedGender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Jenis Kelamin",
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderButton(
                text = "Laki-Laki",
                icon = painterResource(R.drawable.baseline_male_24),
                isSelected = selectedGender == "male",
                onClick = { selectedGender = "male" },
                genderType = GenderType.MALE,
                modifier = Modifier.weight(1f)
            )

            GenderButton(
                text = "Perempuan",
                icon = painterResource(R.drawable.baseline_female_24),
                isSelected = selectedGender == "female",
                onClick = { selectedGender = "female" },
                genderType = GenderType.FEMALE,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

enum class GenderType {
    MALE, FEMALE
}

@Composable
fun GenderButton(
    text: String,
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit,
    genderType: GenderType,
    modifier: Modifier = Modifier
) {
    val genderColor = when (genderType) {
        GenderType.MALE -> colorResource(R.color.blue_gender) // Blue color
        GenderType.FEMALE -> colorResource(R.color.pink_gender) // Pink color
    }

    val backgroundColor = if (isSelected) {
        genderColor.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.background
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            genderColor
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = genderColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                color = genderColor,
                style = Typography.bodySmall
            )
        }
    }
}

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
            text = "Statistik Badan",
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HealthForm(
                onClick = { showHeightPicker = true},
                input = height?.toString() ?: "",
                placeholder = "Tinggi",
                icon = painterResource(R.drawable.rounded_height_24),
                trailingText = "cm",
                modifier = Modifier.weight(1f)
            )
            HealthForm(
                onClick = { showWeightPicker = true},
                input = weight?.toString() ?: "",
                placeholder = "Berat",
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
                    text = "Simpan",
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
                    text = "Simpan",
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
                style = Typography.bodySmall
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colorResource(R.color.gray_300),
            cursorColor = colorResource(R.color.gray_300),
        ),
        leadingIcon = {
            Icon(painter = icon, contentDescription = "")
        },
        trailingIcon = {
            Text(
                trailingText,
                style = Typography.bodyMedium,
                modifier = Modifier.padding(end = 4.dp)
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






@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun FormScreenPreview() {
    BinaSehatTheme {
        FormScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun HeightModalPreview() {
    HeightPicker(
        initialHeight = 175,
        onHeightChange = { newHeight ->
        }

    )
}