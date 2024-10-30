package com.mage.binasehat.ui.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mage.binasehat.R
import com.mage.binasehat.ui.theme.Typography

@Composable
fun GenderOption() {
    var selectedGender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.jenis_kelamin),
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderButton(
                text = stringResource(R.string.laki_laki),
                icon = painterResource(R.drawable.baseline_male_24),
                isSelected = selectedGender == "male",
                onClick = { selectedGender = "male" },
                genderType = GenderType.MALE,
                modifier = Modifier.weight(1f)
            )

            GenderButton(
                text = stringResource(R.string.perempuan),
                icon = painterResource(R.drawable.baseline_female_24),
                isSelected = selectedGender == "female",
                onClick = { selectedGender = "female" },
                genderType = GenderType.FEMALE,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

enum class GenderType {
    MALE, FEMALE
}

@Composable
fun GenderButton(
    text: String,
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit,
    genderType: GenderType,
    modifier: Modifier = Modifier
) {
    val genderColor = when (genderType) {
        GenderType.MALE -> colorResource(R.color.blue_gender) // Blue color
        GenderType.FEMALE -> colorResource(R.color.pink_gender) // Pink color
    }

    val backgroundColor = if (isSelected) {
        genderColor.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.background
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            genderColor
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = genderColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                color = genderColor,
                style = Typography.bodySmall
            )
        }
    }
}