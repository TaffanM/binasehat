package com.mage.binasehat.ui.screen.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.R
import com.mage.binasehat.data.util.Result
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.CustomFillButton
import com.mage.binasehat.ui.screen.components.PasswordTextField
import com.mage.binasehat.ui.screen.components.TextField
import com.mage.binasehat.ui.screen.form.UserRegistrationViewModel
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography

@Composable
fun RegisterScreen(
    navController: NavHostController,
    userRegistrationViewModel: UserRegistrationViewModel = hiltViewModel()
) {

    // Collect state from the ViewModel
    val email by userRegistrationViewModel.email.collectAsState()
    val username by userRegistrationViewModel.username.collectAsState()
    val password by userRegistrationViewModel.password.collectAsState()
    val confirmPassword by userRegistrationViewModel.confirmPassword.collectAsState()

    val emailFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    val formSubmissionState by userRegistrationViewModel.formSubmissionState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
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
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
            RegisterTitleText()

            TextField(
                input = email,
                type = "Email",
                placeholder = stringResource(R.string.masukkan_email),
                onValueChange = { userRegistrationViewModel.updateEmail(it) },
                focusRequester = emailFocusRequester,
                onDone = { usernameFocusRequester.requestFocus() },
                keyboardType = KeyboardType.Email
            )
            TextField(
                input = username,
                type = "Username",
                placeholder = stringResource(R.string.masukkan_username),
                onValueChange = { userRegistrationViewModel.updateUsername(it)  },
                focusRequester = usernameFocusRequester,
                onDone = { passwordFocusRequester.requestFocus() },
                keyboardType = KeyboardType.Text
            )
            PasswordTextField(
                input = password,
                type = stringResource(R.string.katasandi),
                placeholder = stringResource(R.string.masukkan_katasandi),
                onValueChange = { userRegistrationViewModel.updatePassword(it) },
                focusRequester = passwordFocusRequester,
                onDone = { confirmPasswordFocusRequester.requestFocus() },
                keyboardType = KeyboardType.Password
            )
            PasswordTextField(
                input = confirmPassword,
                type = stringResource(R.string.konfirmasi_katasandi),
                placeholder = stringResource(R.string.konfirmasi_katasandi),
                onValueChange = { userRegistrationViewModel.updateConfirmPassword(it) },
                focusRequester = confirmPasswordFocusRequester,
                onDone = { keyboardController?.hide() },
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomFillButton(
                text = stringResource(R.string.daftar),
                onClick = {
                    // Check if any field is empty
                    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(context, R.string.semua_field_diisi, Toast.LENGTH_SHORT).show()
                        return@CustomFillButton
                    }

                    // Check if password and confirm password match
                    if (password != confirmPassword) {
                        Toast.makeText(context, R.string.katasandi_tidak_sama, Toast.LENGTH_SHORT).show()
                        return@CustomFillButton
                    }

                    // Log the registration data (for debugging purposes)
                    Log.d("RegisterScreen", "Username: $username")
                    Log.d("RegisterScreen", "Email: $email")
                    Log.d("RegisterScreen", "Password: $password")
                    Log.d("RegisterScreen", "Confirm Password: $confirmPassword")

                    // Navigate to another screen
                    navController.navigate("form")
                },
                modifier = Modifier
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