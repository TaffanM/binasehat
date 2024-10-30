package com.mage.binasehat.ui.screen.dashboard

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import androidx.tv.material3.Text
import coil.compose.rememberAsyncImagePainter
import com.mage.binasehat.R
import com.mage.binasehat.ui.model.NutritionItem
import com.mage.binasehat.ui.screen.components.AppBar
import com.mage.binasehat.ui.screen.components.DateSlider
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography
import com.mage.binasehat.ui.util.TimeUtility

@Composable
fun DashboardScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val greeting = TimeUtility.getGreeting()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            AppBar(
                navController
            )
            Column(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileImageDashboard("")
                    Text(
                        text = "Username",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = greeting,
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 32.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                DateSlider(
                    modifier = Modifier.padding(top = 32.dp),
                    onDateSelected = {}
                )
                NutritionBar()
            }
        }
    }
}

@Composable
fun NutritionBar() {
    val nutritionData = listOf(
        NutritionItem(stringResource(R.string.karbohidrat), 100, colorResource(R.color.dark_red)),
        NutritionItem(stringResource(R.string.protein), 150, colorResource(R.color.dark_green)),
        NutritionItem(stringResource(R.string.lemak), 75, colorResource(R.color.dark_blue)),
        NutritionItem(stringResource(R.string.gula), 35, colorResource(R.color.dark_yellow))
    )

    val totalValue = nutritionData.sumOf { it.value }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = totalValue.toString(),
                fontSize = 32.sp,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Kkal",
                style = Typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 2.dp),
                color = MaterialTheme.colorScheme.onSurface
            )


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(30))
        ) {
            nutritionData.forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(item.value.toFloat())
                        .fillMaxHeight()
                        .background(item.color)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .border(border = BorderStroke(1.dp, colorResource(R.color.gray_300)), shape = RoundedCornerShape(10))
                .padding(8.dp)
        ) {
            nutritionData.forEachIndexed {index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(8.dp)
                                .background(item.color, RoundedCornerShape(50))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = item.label, fontWeight = FontWeight.Bold, style = Typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                    Text(
                        text = "${item.value} gr",
                        style = Typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (index < nutritionData.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = colorResource(R.color.gray_300),
                        thickness = 1.dp
                    )
                }
            }
        }
        Card(
            modifier = Modifier.padding(top = 24.dp)
                .fillMaxWidth(),
            colors = CardColors(
                containerColor = colorResource(R.color.green_primary),
                contentColor = Color.White,
                disabledContainerColor = colorResource(R.color.green_primary),
                disabledContentColor = Color.White
            ),
            elevation = CardDefaults.elevatedCardElevation()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)

            ) {
                Text(
                    text = stringResource(R.string.kalori_masuk_keluar),
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                        .background(Color.White)
                        .padding(4.dp),

                ) {
                    Text(
                        text = "555.0",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.green_primary),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "Kkal",
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.green_primary),
                    )
                }
            }
        }

    }
}

@Composable
fun ProfileImageDashboard(
    imageUrl: String?,
) {
    Box(
        contentAlignment = Alignment.BottomEnd, // Align pencil icon to the bottom end
        modifier = Modifier.size(56.dp)
    ) {
        // Profile image
        val placeholder = painterResource(id = R.drawable.placeholder_image) // Your placeholder image resource
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            placeholder = placeholder,
            error = placeholder,
            fallback = placeholder,
            onSuccess = {
                Log.d("ProfileImage", "Image loaded successfully")
            },
            onError = {
                Log.e("ProfileImage", "Error loading image: ${it.result.throwable}")
            }
        )

        Image(
            painter = painter,
            contentDescription = "Profile Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(4.dp, MaterialTheme.colorScheme.background, CircleShape)
        )
    }
}


@Composable
fun SettingsButtonLayout(
    icon: Painter,
) {
    Icon(
        painter = icon,
        modifier = Modifier,
        contentDescription = "Settings"
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        navController = NavController(LocalContext.current)
    )
}