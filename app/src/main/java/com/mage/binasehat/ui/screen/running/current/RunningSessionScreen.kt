package com.mage.binasehat.ui.screen.running.current

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mage.binasehat.R
import com.mage.binasehat.ui.screen.components.CurrentRunStatsCard
import com.mage.binasehat.ui.screen.components.Map
import com.mage.binasehat.ui.util.ComposeUtility
import com.mage.binasehat.ui.util.LocationUtility
import kotlinx.coroutines.delay

@Composable
fun CurrentRunScreen(
    navController: NavController,
    viewModel: CurrentRunViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        LocationUtility.checkAndRequestLocationSetting(context as Activity)
    }
    var isRunningFinished by rememberSaveable { mutableStateOf(false) }
    var shouldShowRunningCard by rememberSaveable { mutableStateOf(false) }
    val runState by viewModel.currentRunStateWithCalories.collectAsStateWithLifecycle()
    val runningDurationInMillis by viewModel.runningDurationInMillis.collectAsStateWithLifecycle()

    val uploadResult by viewModel.uploadResult.observeAsState()

    LaunchedEffect(key1 = Unit) {
        delay(ComposeUtility.slideDownInDuration + 200L)
        shouldShowRunningCard = true
    }

    uploadResult?.let { result ->
        result.onSuccess {
            Toast.makeText(context, "Run data uploaded successfully!", Toast.LENGTH_SHORT).show()
        }.onFailure {
            Toast.makeText(context, "Failed to upload run data: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Map(
            pathPoints = runState.currentRunState.pathPoints,
            isRunningFinished = isRunningFinished,
        ) {
            viewModel.finishRun(it)
            navController.navigateUp()
        }
        TopBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(24.dp),
            onNavigateUp = navController::navigateUp
        )
        ComposeUtility.SlideUpAnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = shouldShowRunningCard
        ) {
            CurrentRunStatsCard(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                onPlayPauseButtonClick = viewModel::playPauseTracking,
                runState = runState,
                durationInMillis = runningDurationInMillis,
                onFinish = { isRunningFinished = true }
            )
        }

    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    IconButton(
        onClick = onNavigateUp,
        modifier = modifier
            .size(32.dp)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                clip = true
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .padding(4.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}