package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography
import java.security.Key

@Composable
fun PasswordTextField(
    input: String,
    type: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onDone: () -> Unit,
    keyboardType: KeyboardType
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = type,
            color = MaterialTheme.colorScheme.onSurface,
            style = Typography.bodyMedium
        )
        OutlinedTextField(
            value = input,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .focusRequester(focusRequester),
            textStyle = Typography.bodyMedium,
            placeholder = {
                Text(
                    placeholder,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    style = Typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.green_primary),
                unfocusedBorderColor = colorResource(R.color.gray_300),
                cursorColor = colorResource(R.color.gray_300),
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = colorResource(R.color.gray),
                disabledTextColor = colorResource(R.color.gray_300),
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (keyboardType == KeyboardType.Password) {
                    val image = if (passwordVisible) painterResource(R.drawable.round_visibility_off_24) else painterResource(R.drawable.round_visibility_24)
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(painter = image, contentDescription = null)
                    }
                }
            }

        )


    }
}