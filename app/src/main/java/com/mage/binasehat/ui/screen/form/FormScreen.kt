package com.mage.binasehat.ui.screen.form

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.BirthEditText
import com.mage.binasehat.ui.screen.components.BodyStats
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.GenderOption
import com.mage.binasehat.ui.screen.components.HeightPicker
import com.mage.binasehat.ui.screen.components.LoadingDialog
import com.mage.binasehat.ui.screen.components.SuccessDialog
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography
import com.mage.binasehat.data.util.Result

@Composable
fun FormScreen(
    navController: NavHostController,
    userRegistrationViewModel: UserRegistrationViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        Log.d("FormScreen", "Username: ${userRegistrationViewModel.username.value}")
        Log.d("FormScreen", "Email: ${userRegistrationViewModel.email.value}")
        Log.d("FormScreen", "Password: ${userRegistrationViewModel.password.value}")
    }

    // Form input state
    var birt by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var tall by remember { mutableStateOf("") }
    var weigh by remember { mutableStateOf("") }

    // Observe form submission state from the ViewModel
    val formSubmissionState by userRegistrationViewModel.formSubmissionState.collectAsState()
    var showLoadingDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Handle form submission state
    when (formSubmissionState) {
        is Result.Loading -> showLoadingDialog = true
        is Result.Success -> {
            showLoadingDialog = false
            showSuccessDialog = true
        }
        is Result.Error -> {
            showLoadingDialog = false
            val errorMessage = (formSubmissionState as Result.Error).message
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    "$errorMessage Internal Server Error, Try Again Later ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        is Result.Idle -> { /* Do nothing */ }
    }

    Box(
        modifier = Modifier.fillMaxSize()
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

            // Bind inputs to ViewModel state
            BirthEditText(value = birt, onValueChange = { birt = it })
            GenderOption(value = gender, onValueChange = { gender = it })
            BodyStats(
                tallValue = tall,
                weighValue = weigh,
                onTallChange = { tall = it },
                onWeighChange = { weigh = it }
            )

            // Form submission button
            CustomFillButton(
                text = stringResource(R.string.kirim),
                onClick = {
                    Log.d("FormScreen", "Birth: $birt, Gender: $gender, Tall: $tall, Weigh: $weigh")

                    // Check if all required fields are filled
                    if (birt.isEmpty() || gender.isEmpty() || tall.isEmpty() || weigh.isEmpty()) {
                        Toast.makeText(context, R.string.semua_field_diisi, Toast.LENGTH_SHORT).show()
                        return@CustomFillButton
                    }

                    // Submit the form by calling the ViewModel's submitForm function
                    userRegistrationViewModel.submitForm(
                        birt = birt,
                        gender = gender,
                        tall = tall.toFloat(),
                        weigh = weigh.toFloat()
                    )
                },
                modifier = Modifier
            )
        }

        // Loading dialog while the form is being submitted
        if (showLoadingDialog) {
            LoadingDialog(
                onDismiss = { showLoadingDialog = false }
            )
        }

        // Success dialog once the form is successfully submitted
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