package com.mage.binasehat.ui.screen.food

import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.tv.material3.Text
import com.mage.binasehat.R
import com.mage.binasehat.data.remote.response.FoodListItem
import com.mage.binasehat.ui.screen.components.AppBar
import com.mage.binasehat.ui.screen.components.CameraX
import com.mage.binasehat.ui.screen.components.DateSlider
import com.mage.binasehat.ui.screen.dashboard.DashboardViewModel
import com.mage.binasehat.ui.screen.dashboard.SettingsButtonLayout
import com.mage.binasehat.ui.theme.PlusJakartaSans
import com.mage.binasehat.ui.theme.Typography
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodScreen(
    navController: NavController,
    foodViewModel: FoodViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenWidth = configuration.screenWidthDp.dp

    // Set desired size as a percentage of the screen dimensions
    val imageSize = (screenWidth * 0.30f) // 30% of the screen width

    // Observing the food history response from the ViewModel
    val foodHistoryResponse by foodViewModel.foodHistory.collectAsState()
    val loading by foodViewModel.loading.collectAsState()
    val errorMessage by foodViewModel.errorMessage.collectAsState()

    val selectedDate = remember { mutableStateOf(LocalDate.now().toString()) }

    LaunchedEffect(Unit) {
        foodViewModel.getFoodHistory(date = selectedDate.value)
        dashboardViewModel.filterDailyCaloriesByDate(date = selectedDate.value)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Only LazyColumn is needed for scrolling, no need to apply verticalScroll to parent Column
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
        ) {
            stickyHeader {
                AppBar(navController)
            }
            // Add header item before the list
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.asupan_makanan),
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Date slider, triggering food history fetch
                    DateSlider(
                        modifier = Modifier.padding(top = 32.dp),
                        onDateSelected = {
                            selectedDate.value = it.toString()
                            Log.d("FoodScreen", "Selected date: $it")
                            foodViewModel.getFoodHistory(date = selectedDate.value)
                            dashboardViewModel.filterDailyCaloriesByDate(date = selectedDate.value)
                        }
                    )

                    Text(
                        text = stringResource(R.string.makananmu_hari_ini),
                        style = Typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 32.dp)
                    )

                    Text(
                        text = "${(foodHistoryResponse?.totalNutrition?.calories ?: 0)} Cal",
                        fontSize = 32.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // LazyColumn content (Food History List)
            if (!loading && errorMessage == null) {
                val foodList = foodHistoryResponse?.foodList
                if (foodList.isNullOrEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.food_history_empty),
                            style = Typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(foodList) { foodItem ->
                        FoodHistoryItem(foodItem) // Display each food history item
                    }
                }
            }
        }

        // Floating Action Button to add food
        AddFoodExtendedFab(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                navController.navigate("scan") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun FoodHistoryItem(foodItem: FoodListItem) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Define the formatter for the input string date format
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        // Parse the consumedAt string into a LocalDateTime
        val consumedAt = try {
            LocalDateTime.parse(foodItem.consumedAt, inputFormatter)
        } catch (e: Exception) {
            // Handle cases where parsing fails (e.g., invalid date format)
            LocalDateTime.now() // Fallback to the current time if parsing fails
        }

        // Define the formatter for the desired display format
        val displayFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())

        // Format the parsed date
        val formattedDate = consumedAt.format(displayFormatter)

        Text(
            text = foodItem.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Calories: ${foodItem.calories} | Portion: ${foodItem.portion}",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.dimakan_pada) + " : " + formattedDate,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
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
    FoodScreen(NavController(LocalContext.current))
}