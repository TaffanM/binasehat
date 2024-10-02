package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R

@Composable
fun CustomOutlinedButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        androidx.compose.material3.OutlinedButton(
            onClick =  onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, color = colorResource(R.color.green_primary))
        ) {
            Text(
                text = text,
                color = colorResource(R.color.green_primary),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        }


}