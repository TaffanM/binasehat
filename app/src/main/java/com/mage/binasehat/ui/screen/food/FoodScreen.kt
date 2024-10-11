package com.mage.binasehat.ui.screen.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.DateSlider
import com.mage.binasehat.ui.screen.dashboard.SettingsButtonLayout
import com.mage.binasehat.ui.theme.Typography

@Composable
fun FoodScreen(

) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Set desired size as a percentage of the screen dimensions
    val imageSize = (screenWidth * 0.30f) // 15% of the screen width

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(imageSize),
                    painter = painterResource(id = R.drawable.logo_1),
                    contentDescription = "Logo"
                )
                Text(
                    text = "BinaSehat",
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                ) { SettingsButtonLayout(icon = painterResource(R.drawable.rounded_settings_24)) }
            }
            Column(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.asupan_makanan),
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                DateSlider(
                    modifier = Modifier.padding(top = 32.dp),
                    onDateSelected = {}
                )
                Text(
                    text = stringResource(R.string.makananmu_hari_ini),
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
        AddFoodExtendedFab(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {}
        )
    }

}

@Composable
fun AddFoodExtendedFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(R.drawable.rounded_add_2_24),
                contentDescription = "Add"
            )
        },
        text = {
            Text(
                text = stringResource(R.string.tambah_makanan),
                style = Typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = Color.White
        )},
        modifier = modifier
            .padding(16.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun FoodScreenPreview() {
    FoodScreen()
}