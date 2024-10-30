package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.BinaSehatTheme
import com.mage.binasehat.ui.theme.Typography

@Composable
fun BackButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        modifier = modifier
    ) {
        BackButtonLayout(text, icon)
    }
}

@Composable
fun BackButtonLayout(
    text: String,
    icon: Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = icon,
            modifier = Modifier,
            contentDescription = "Next"
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = Typography.bodyMedium
        )

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun BackButtonPreview() {
    BinaSehatTheme(
        darkTheme = false,
    ) {
        BackButton(
            text = "Login",
            icon = painterResource(R.drawable.round_arrow_back_ios_24),
            onClick = {}
        )
    }
}