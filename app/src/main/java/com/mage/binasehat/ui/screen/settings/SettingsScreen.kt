package com.mage.binasehat.ui.screen.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.LanguageDialog
import com.mage.binasehat.ui.screen.login.LoginViewModel
import com.mage.binasehat.ui.theme.Typography
import com.mage.binasehat.ui.util.UserPreferences

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isDarkMode = UserPreferences.getTheme(context)

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BackButton(
                text = stringResource(R.string.kembali),
                onClick = { navController.popBackStack() },
                icon = painterResource(R.drawable.round_arrow_back_ios_24)
            )
            Text(
                text = stringResource(R.string.pengaturan),
                style = Typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            HorizontalDivider()
            Text(
                text = stringResource(R.string.akun),
                style = Typography.titleMedium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )
            SettingsOption(
                title = stringResource(R.string.detail_akun),
                icon = painterResource(R.drawable.rounded_person_edit_24),
                onClick = {
                    navController.navigate("account_detail") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
            Text(
                text = stringResource(R.string.preferensi),
                style = Typography.titleMedium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )
            SettingsOption(
                title = stringResource(R.string.bahasa),
                icon = painterResource(R.drawable.ic_translate),
                onClick = {
                    showDialog = true
                }
            )
            DarkModeOption(
                title = stringResource(R.string.mode_malam),
                icon = painterResource(R.drawable.rounded_dark_mode_24),
                onToggle = { newDarkModeState ->
                    Log.d("SettingsScreen", "Dark mode toggle: $newDarkModeState")
                    UserPreferences.saveTheme(context, newDarkModeState)
                },
                initialChecked = isDarkMode

            )
            SettingsOption(
                title = stringResource(R.string.preferensi_notifikasi),
                icon = painterResource(R.drawable.rounded_edit_notifications_24),
                onClick = {}
            )
            Text(
                text = stringResource(R.string.umpan_balik),
                style = Typography.titleMedium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )
            SettingsOption(
                title = stringResource(R.string.umpan_balik),
                icon = painterResource(R.drawable.rounded_feedback_24),
                onClick = {}
            )

            LanguageDialog(
                isOpen = showDialog,
                onDismiss = { showDialog = false },
                onLanguageSelected = { selectedLanguage ->
                    // Handle language selection here
                    Log.d("SettingsScreen", "Selected language: $selectedLanguage")
                    UserPreferences.saveLanguage(context, selectedLanguage)
                    UserPreferences.updateLocale(context)
                    showDialog = false
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            LogoutButton(
                text = stringResource(R.string.keluar),
                onClick = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .padding(bottom = 32.dp)

            )


        }
    }

}

@Composable
fun SettingsOption(
    title: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = title,
            style = Typography.titleMedium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.rounded_chevron_forward_24),
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DarkModeOption(
    title: String,
    icon: Painter,
    onToggle: (Boolean) -> Unit,
    initialChecked: Boolean
) {
    // Use rememberSaveable to preserve the state across recompositions
    var checked by rememberSaveable { mutableStateOf(initialChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = title,
            style = Typography.titleMedium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onToggle(it)
            }
        )
    }
}

@Composable
fun LogoutButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.dark_red),
            contentColor = colorResource(R.color.white_bg)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        navController = NavController(LocalContext.current)
    )

}