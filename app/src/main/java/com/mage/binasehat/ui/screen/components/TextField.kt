package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography

@Composable
fun TextField(
    input: String,
    type: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onDone: () -> Unit,
    keyboardType: KeyboardType
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = type,
            color = colorResource(R.color.gray_300),
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
                    color = colorResource(R.color.gray_300).copy(alpha = 0.5f),
                    style = Typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.green_primary),
                unfocusedBorderColor = colorResource(R.color.gray_300),
                cursorColor = colorResource(R.color.gray_300),
                focusedTextColor = colorResource(R.color.gray_300),
                unfocusedTextColor = colorResource(R.color.gray_300),
                disabledTextColor = colorResource(R.color.gray_300),
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)

        )


    }
}