package com.mage.binasehat.ui.screen.workout.detail

import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.mage.binasehat.R
import com.mage.binasehat.data.local.db.mapper.ExerciseMapper
import com.mage.binasehat.data.local.entity.ScheduleExerciseEntity
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.remote.response.DetailExercise
import com.mage.binasehat.data.remote.response.DetailExerciseRespone
import com.mage.binasehat.data.util.Result
import com.mage.binasehat.data.util.UiState
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.CustomFillButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailWorkoutScreen(
    workoutId: Int,
    navigateBack: () -> Unit,
    navigateToInteractiveArea: (Long) -> Unit,
    detailWorkoutViewModel: DetailWorkoutViewModel = hiltViewModel()

) {
    LaunchedEffect(workoutId) {
        detailWorkoutViewModel.getWorkoutById(workoutId.toLong())
    }


    val exerciseState by detailWorkoutViewModel.exercise.collectAsState()
    when (exerciseState) {
        is Result.Loading -> {
            detailWorkoutViewModel.getWorkoutById(workoutId.toLong())
        }

        is Result.Success -> {
            val data = (exerciseState as Result.Success<DetailExerciseRespone>).data

            // Assuming data is of type DetailExerciseRespone, make sure to check and map it if needed
            // Map or cast data correctly if needed
            val exercise = ExerciseMapper.mapDetailResponseToExercise(data)

            data.data?.let {
                DetailContent(
                    navigateBack,
                    exercise,
                    it,
                    navigateToInteractiveArea,
                    addToSchedule = { schedule -> detailWorkoutViewModel.addWorkoutSchedule(schedule) },
                )
            }
        }

        is Result.Error -> {
            val error = (exerciseState as Result.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.error_message),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Result.Idle -> {

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    navigateBack: () -> Unit,
    exercise: Exercise,
    detailExercise: DetailExercise,
    navigateToInteractiveArea: (Long) -> Unit,
    addToSchedule: (ScheduleExerciseEntity) -> String,

    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BackButton(
                    onClick = { navigateBack() },
                    text = stringResource(R.string.list_kebugaran),
                    icon = painterResource(R.drawable.round_arrow_back_ios_24),
                    modifier = Modifier
                        .padding(start = 32.dp, top = 32.dp, bottom = 8.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(exercise.name ?: "", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            WorkoutTutorial((exercise.Gif))
            Spacer(modifier = Modifier.height(100.dp))
            WorkoutInformationTab(detailExercise)


        }



        if (exercise.isSupportInteractive) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CustomFillButton(
                    onClick = {
                        navigateToInteractiveArea(exercise.id?.toLong() ?: 0)
                    },
                    text = stringResource(R.string.mulai_latihan_interaktif),
                    modifier = Modifier

                        .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
                )
            }

        }
    }
}

@Composable
fun GifImage(
    @DrawableRes gifResourceId: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context)
                .data(gifResourceId)
                .size(Size.ORIGINAL)
                .build(),
            imageLoader = imageLoader
        ),
        contentDescription = "Exercise demonstration",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@Composable
fun WorkoutTutorial(
    @DrawableRes gifResourceId: Int,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabTitles = listOf("Animation", "Video")

    Column {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            modifier = Modifier.padding(
                horizontal = 60.dp
            )
        ) {
            // Create tabs
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Content based on selected tab
        Box(modifier.padding(16.dp)) {
            when (selectedTabIndex) {
                0 -> GifImage(gifResourceId)
                1 -> WorkoutVideo()
            }
        }

    }
}

@Composable
fun WorkoutVideo() {
    Text("Sorry , No Available for now", style = MaterialTheme.typography.bodyMedium.copy(
        textAlign = TextAlign.Center
    ))

}

@Composable
fun WorkoutInformationTab(exercise: DetailExercise) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabTitles = listOf("Overview", "Step by Step", "Muscle Target")

    Column(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(16.dp)) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        ) {
            // Create tabs
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> OverviewContent(exercise.overview ?: "")
            1 -> StepByStepContent(exercise.step ?: "")
            2 -> MuscleTargetContent(exercise.muscle?.name ?: "")
        }
    }
}

@Composable
fun OverviewContent(explain: String) {
    // Content for Overview tab
    Text(
        explain, style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
    )
}

@Composable
fun StepByStepContent(step: String) {
    val stepsArray = step.split(", ").toTypedArray()

    val formattedSteps = formatSteps(stepsArray)

    // Content for Step by Step tab
    Text(
        formattedSteps, style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun MuscleTargetContent(muscle : String) {
    // Content for Muscle Target tab
    Text("Muscle Target for this exercise is $muscle")
}

fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}

fun formatSteps(steps: Array<String>): String {
    val formattedSteps = StringBuilder()

    for ((index, step) in steps.withIndex()) {
        formattedSteps.append("${index + 1}. $step\n")
    }

    return formattedSteps.toString().trimEnd()
}