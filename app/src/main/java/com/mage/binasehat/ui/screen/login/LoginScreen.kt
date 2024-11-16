package com.mage.binasehat.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.R
import com.mage.binasehat.data.util.Result
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.CustomOutlinedButton
import com.mage.binasehat.ui.screen.components.LoadingDialog
import com.mage.binasehat.ui.screen.components.PasswordTextField
import com.mage.binasehat.ui.screen.components.TextField
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    val loginResult by loginViewModel.loginResult.collectAsState()
    var showLoadingDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    when (loginResult) {
        is Result.Loading -> {
            showLoadingDialog = true

        }
        is Result.Success -> {
            showLoadingDialog = false
            LaunchedEffect(Unit) {
                navController.navigate("main") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
        is Result.Error -> {
            showLoadingDialog = false
            val errorMessage = (loginResult as Result.Error).message
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    "$errorMessage Internal Server Error, Try Again Later ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Log.d("LoginScreen", "Error: $errorMessage")
        }
        is Result.Idle -> {

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.mobile_login),
                contentDescription = "Login",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),

            )
            LoginTitleText()
            TextField(
                input = emailInput,
                type = "Email",
                placeholder = stringResource(R.string.masukkan_email),
                onValueChange = { newInput ->
                    emailInput = newInput
                },
                focusRequester = emailFocusRequester,
                onDone = {
                    passwordFocusRequester.requestFocus()
                },
                keyboardType = KeyboardType.Email
            )
            PasswordTextField(
                input = passwordInput,
                type = stringResource(R.string.katasandi),
                placeholder = stringResource(R.string.masukkan_katasandi),
                onValueChange = { newInput ->
                    passwordInput = newInput
                },
                focusRequester = passwordFocusRequester,
                onDone = {
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Password
            )
            CustomOutlinedButton(
                stringResource(R.string.login),
                onClick = {
                    if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                        Toast.makeText(context, R.string.semua_field_diisi, Toast.LENGTH_SHORT).show()
                        return@CustomOutlinedButton
                    }

                    loginViewModel.login(emailInput, passwordInput)
//                    navController.navigate("main") {
//                        popUpTo(navController.graph.startDestinationId) {
//                            inclusive = true
//                        }
//                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            )
            OrDivider()
            CustomFillButton(
                stringResource(R.string.daftar),
                onClick = {
                    navController.navigate("register") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
            )
        }

        if (showLoadingDialog) {
            LoadingDialog(
                onDismiss = { showLoadingDialog = false }
            )
        }
    }


}

@Composable
fun LoginTitleText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 32.dp)
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.login_desc),
            style = Typography.bodyMedium
        )
    }

}


@Composable
fun OrDivider(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Text(
            text = stringResource(R.string.atau),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = Typography.bodyMedium
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LoginScreenPreview() {
    BinaSehatTheme(
        darkTheme = false
    ) {
        LoginScreen(rememberNavController())
    }
}

