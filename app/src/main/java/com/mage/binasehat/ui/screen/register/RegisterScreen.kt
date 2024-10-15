package com.mage.binasehat.ui.screen.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.TextField
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography

@Composable
fun RegisterScreen(
    navController: NavHostController
) {

    var emailRegister by remember { mutableStateOf("") }
    var usernameRegister by remember { mutableStateOf("") }
    var passwordRegister by remember { mutableStateOf("") }
    var confirmPasswordRegister by remember { mutableStateOf("") }

    val emailFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

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
                text = stringResource(R.string.login),
                icon = painterResource(R.drawable.round_arrow_back_ios_24),
                onClick = {
                    navController.popBackStack()
                }
            )
            RegisterTitleText()
            TextField(
                input = emailRegister,
                type = "Email",
                placeholder = stringResource(R.string.masukkan_email),
                onValueChange = { newItem ->
                    emailRegister = newItem
                },
                focusRequester = emailFocusRequester,
                onDone = {
                    usernameFocusRequester.requestFocus()
                },
                keyboardType = KeyboardType.Email
            )
            TextField(
                input = usernameRegister,
                type = "Username",
                placeholder = stringResource(R.string.masukkan_username),
                onValueChange = { newItem ->
                    usernameRegister = newItem
                },
                focusRequester = usernameFocusRequester,
                onDone = {
                    passwordFocusRequester.requestFocus()
                },
                keyboardType = KeyboardType.Text
            )
            TextField(
                input = passwordRegister,
                type = stringResource(R.string.katasandi),
                placeholder = stringResource(R.string.masukkan_katasandi),
                onValueChange = { newItem ->
                    passwordRegister = newItem
                },
                focusRequester = passwordFocusRequester,
                onDone = {
                    confirmPasswordFocusRequester.requestFocus()
                },
                keyboardType = KeyboardType.Password
            )
            TextField(
                input = confirmPasswordRegister,
                type = stringResource(R.string.konfirmasi_katasandi),
                placeholder = stringResource(R.string.konfirmasi_katasandi),
                onValueChange = { newItem ->
                    confirmPasswordRegister = newItem
                },
                focusRequester = confirmPasswordFocusRequester,
                onDone = {
                    keyboardController?.hide()
                },
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomFillButton(
                text = stringResource(R.string.daftar),
                onClick = {
                    navController.navigate("form") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )



        }

    }

}

@Composable
fun RegisterTitleText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = stringResource(R.string.daftar),
            fontSize = 24.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.daftar_deskripsi),
            style = Typography.bodyMedium,
            textAlign = TextAlign.Start
        )
    }

}



@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun RegisterScreenPreview() {
    BinaSehatTheme(
        darkTheme = false
    ) {
        RegisterScreen(rememberNavController())
    }
}