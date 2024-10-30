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
import com.mage.binasehat.ui.screen.components.BirthEditText
import com.mage.binasehat.ui.screen.components.BodyStats
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.CustomOutlinedButton
import com.mage.binasehat.ui.screen.components.GenderOption
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
                },
                modifier = Modifier
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
            text = stringResource(R.string.form_desc),
            style = Typography.bodyMedium,
            textAlign = TextAlign.Start
        )

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun FormScreenPreview() {
    BinaSehatTheme(
        darkTheme = false,
    ) {
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