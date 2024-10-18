package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Icon
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography

@Composable
fun LanguageDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(R.string.pilih_bahasa),
                    style = Typography.titleMedium
                )
            },
            text = {
                Column {
                    LanguageOption(language = "en", textLanguage = stringResource(R.string.inggris), painterResource(R.drawable.uk), onLanguageSelected)
                    LanguageOption(language = "in", textLanguage = stringResource(R.string.indonesia), icon = painterResource(R.drawable.indonesia), onLanguageSelected)
                    // Add more languages here if needed
                }
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.batal),
                        style = Typography.bodySmall,
                        color = colorResource(R.color.white_200)
                    )
                }
            }
        )
    }
}

@Composable
fun LanguageOption(
    language: String,
    textLanguage: String,
    icon: Painter,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onLanguageSelected(language)
            },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = language,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp),
            tint = Color.Unspecified
        )
        Text(
            text = textLanguage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            style = Typography.titleMedium
        )

    }

}