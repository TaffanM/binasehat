package com.mage.binasehat.ui.screen.running.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mage.binasehat.R
import com.mage.binasehat.data.model.Run
import com.mage.binasehat.domain.model.CurrentRunStateWithCalories
import com.mage.binasehat.extension.getDisplayDate
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.RunInfoDialog
import com.mage.binasehat.ui.screen.running.RunViewModel
import com.mage.binasehat.ui.theme.Typography
import com.mage.binasehat.ui.util.TimeUtility

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RunningMainScreen(
    navController: NavController,
    state: RunningMainScreenState,
    runViewModel: RunViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val durationInMillis by runViewModel.durationInMillis.collectAsStateWithLifecycle()

    // Permission request launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Toast.makeText(context, if (isGranted) "Location permission granted" else "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    )

    // Notification permission request launcher
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Toast.makeText(context, if (isGranted) "Notification permission granted" else "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    )

    // Check and request permission when the screen loads
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // UI content for RunningMainScreen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 72.dp, start = 32.dp, end = 32.dp, top = 32.dp) // Add padding to accommodate the FAB
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackButton(
                        text = stringResource(R.string.kembali),
                        onClick = { navController.popBackStack() },
                        icon = painterResource(R.drawable.round_arrow_back_ios_24),
                    )
                    Spacer(modifier = Modifier.weight(1f)) // Pushes the content to the end
                    Text(
                        text = stringResource(R.string.lari),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            item {
                TotalProgressCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    state = state
                )
            }

            if (durationInMillis > 0) {
                item {
                    CurrentRunningCard(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .clickable(onClick = {
                                navController.navigate("currentRunningScreen") {
                                    launchSingleTop = true
                                }
                            }),
                        durationInMillis = durationInMillis,
                        runState = state.currentRunStateWithCalories,
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.aktivitas_terakhir),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.semua),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                role = Role.Button,
                                onClick = {
                                    navController.navigate("historyRunningScreen") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                    )
                }
            }

            if (state.runList.isEmpty()) {
                item {
                    EmptyRunListView()
                }
            } else {
                item {
                    RecentRunList(
                        runList = state.runList,
                        onItemClick = {
                            runViewModel.showRun(run = it)
                        },
                        modifier = Modifier.padding()
                    )
                }

            }

            // Show RunInfoDialog if currentRunInfo is not null
            state.currentRunInfo?.let {
                item {
                    RunInfoDialog(
                        run = it,
                        onDismiss = runViewModel::dismissRunDialog,
                        onDelete = runViewModel::deleteRun
                    )
                }
            }
        }

        // Extended FAB
        ExtendedFloatingActionButton(
            onClick = {
                navController.navigate("currentRunningScreen") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.rounded_directions_run_24),
                    contentDescription = "Add Run"
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.lari_sekarang),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    }
}


@Composable
private fun CurrentRunningCard(
    modifier: Modifier = Modifier,
    runState: CurrentRunStateWithCalories,
    durationInMillis: Long
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.running_boy),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        CurrentSessionInfo(
            modifier = Modifier.weight(1f),
            durationInMillis = durationInMillis
        )
        CurrentSessionStats(
            distanceInMeters = runState.currentRunState.distanceInMeters,
            caloriesBurnt = runState.caloriesBurnt
        )
    }
}

@Composable
private fun CurrentSessionInfo(
    modifier: Modifier = Modifier,
    durationInMillis: Long
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.sesi_saat_ini),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = TimeUtility.getFormattedStopwatchTime(durationInMillis),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White
            )
        )
    }
}

@Composable
private fun CurrentSessionStats(
    distanceInMeters: Int,
    caloriesBurnt: Int
) {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "${distanceInMeters / 1000f} km",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = "${caloriesBurnt.toInt()} kcal",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White
            )
        )
    }
}

@Composable
fun TotalProgressCard(
    modifier: Modifier = Modifier,
    state: RunningMainScreenState
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        TotalProgressHeader(
            modifier = Modifier.padding(24.dp)
        )
        TotalProgressStats(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp),
            state = state
        )
    }
}

@Composable
private fun TotalProgressHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.total_progres),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun TotalProgressStats(
    modifier: Modifier = Modifier,
    state: RunningMainScreenState
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RunningStatsItem(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.running_boy),
            unit = "km",
            value = String.format("%.1f", state.totalDistanceInKm)
        )
        VerticalDivider(
            modifier = Modifier
                .height(32.dp)
                .width(1.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )
        RunningStatsItem(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.stopwatch),
            unit = stringResource(R.string.jam),
            value = String.format("%.1f", state.totalDurationInHr)
        )
        VerticalDivider(
            modifier = Modifier
                .height(32.dp)
                .width(1.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )
        RunningStatsItem(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.fire),
            unit = "Cal",
            value = String.format("%.1f", state.totalCaloriesBurnt.toDouble())
        )
    }
}

@Composable
fun RunningStatsItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    unit: String,
    value: String
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(
            modifier = Modifier
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
            )
            Text(
                text = unit,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun RunItem(
    modifier: Modifier = Modifier,
    run: Run,
    showTrailingIcon: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            bitmap = run.img.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.size(16.dp))
        RunInfo(
            modifier = Modifier
                .weight(1f),
            run = run
        )
        if (showTrailingIcon)
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.rounded_chevron_forward_24),
                contentDescription = "More info",
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.onSurface
            )
    }
}

@Composable
private fun RunInfo(
    modifier: Modifier = Modifier,
    run: Run
) {
    Column(modifier) {
        Text(
            text = run.timestamp.getDisplayDate(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal
            ),
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "${(run.distanceInMeters / 1000f)} km",
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Row {
            Text(
                text = "${run.caloriesBurned} kcal",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal
                ),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "${run.avgSpeedInKMH} km/hr",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal
                ),
            )
        }
    }
}

@Composable
private fun RecentRunList(
    modifier: Modifier = Modifier,
    runList: List<Run>,
    onItemClick: (Run) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(bottom = 8.dp)
            .wrapContentHeight()
    ) {
        Column {
            runList.forEachIndexed { i, run ->
                Column(
                    modifier = Modifier
                ) {
                    RunItem(
                        run = run,
                        modifier = Modifier
                            .clickable { onItemClick(run) }
                            .padding(16.dp)
                    )
                    if (i < runList.lastIndex)
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .width(200.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun EmptyRunListView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.round_calendar_month_24),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(16.dp))
        val inlineContentMap = mapOf(
            "run_icon_img" to InlineTextContent(
                placeholder = Placeholder(
                    MaterialTheme.typography.bodyLarge.fontSize,
                    MaterialTheme.typography.bodyLarge.fontSize,
                    PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.rounded_directions_run_24),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.belum_ada_sesi_lari))
                appendInlineContent(id = "run_icon_img")
            },
            inlineContent = inlineContentMap,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}
