package com.mage.binasehat.ui.screen.workout.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mage.binasehat.R
import com.mage.binasehat.data.util.UiState
import com.mage.binasehat.ui.screen.components.BackButton
import com.mage.binasehat.ui.screen.components.ExerciseGridCard
import com.mage.binasehat.ui.screen.components.ExerciseHorizontalCard
import com.mage.binasehat.ui.screen.components.ListSection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutMainScreen(
    navController: NavController,
    navigateToDetail: (Long) -> Unit,
    viewModel: WorkoutMainViewModel = hiltViewModel(),
    navigateToDetailSchedule: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val todaySchedule by viewModel.todaySchedule.collectAsState()
    val muscleTargetState by viewModel.muscleTargetState.collectAsState()
    val activeMuscleId by viewModel.activeMuscleId.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularWorkout()
        viewModel.fetchListMuscle()
        viewModel.setActiveMuscleId(4)
    }

    if (muscleTargetState is UiState.Success) {
        val muscles = (muscleTargetState as UiState.Success).data
        if (muscles.isNotEmpty() && activeMuscleId == null) {
            viewModel.setActiveMuscleId(muscles.first().id)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackButton(
                        text = stringResource(R.string.kembali),
                        onClick = { navController.popBackStack() },
                        icon = painterResource(R.drawable.round_arrow_back_ios_24),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.kebugaran),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            item {
                // Section for popular exercises
                viewModel.exercisePopular.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            ListSection(
                                title = stringResource(id = R.string.section_favorite_title),
                                subtitle = stringResource(id = R.string.section_favorite_subtitle)
                            ) {

                            }
                            viewModel.fetchPopularWorkout() // Fetch popular workouts
                        }
                        is UiState.Success -> {
                            if (uiState.data.isEmpty()) {
                                Text(stringResource(id = R.string.empty_exercise_message))
                            } else {
                                ListSection(
                                    title = stringResource(id = R.string.section_favorite_title),
                                    subtitle = stringResource(id = R.string.section_favorite_subtitle)
                                ) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                    ) {
                                        items(uiState.data) { exercise ->
                                            ExerciseHorizontalCard(
                                                exercise = exercise,
                                                navigateToDetail = navigateToDetail
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        is UiState.Error -> {
                            Text(stringResource(id = R.string.error_message))
                        }
                    }
                }

//                // Section for today's schedule
//                todaySchedule.let { uiState ->
//                    when (uiState) {
//                        is UiState.Loading -> {
//                            Text("Loading ...")
//                        }
//                        is UiState.Success -> {
//                            val exerciseSummary = uiState.data
//                            if (exerciseSummary != TodayExerciseSummary("N/A", "", 0, 0, 0)) {
//                                Card(
//                                    colors = CardDefaults.cardColors(
//                                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1F),
//                                    ),
//                                    modifier = Modifier.padding(16.dp)
//                                ) {
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.spacedBy(36.dp),
//                                        modifier = Modifier.padding(16.dp)
//                                    ) {
//                                        Column(modifier = Modifier.weight(2f)) {
//                                            Row {
//                                                Text(exerciseSummary.muscleTarget)
//                                            }
//                                            val percentageFinished = if (exerciseSummary.total_exercise_today > 0) {
//                                                (exerciseSummary.total_exercise_finished.toDouble() / exerciseSummary.total_exercise_today.toDouble() * 100).toInt()
//                                            } else {
//                                                0
//                                            }
//                                            Spacer(modifier = modifier.height(12.dp))
//                                            Text(
//                                                "$percentageFinished% Completed",
//                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
//                                            )
//                                            LinearProgressIndicator(
//                                                progress = percentageFinished / 100f,
//                                                trackColor = MaterialTheme.colorScheme.primary,
//                                                color = MaterialTheme.colorScheme.primary,
//                                                modifier = Modifier.padding(vertical = 8.dp)
//                                            )
//                                        }
//                                        Button(
//                                            modifier = Modifier.weight(1f),
//                                            onClick = { navigateToDetailSchedule(exerciseSummary.dateString) }
//                                        ) {
//                                            Icon(painterResource(R.drawable.round_arrow_forward_24), contentDescription = null)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        is UiState.Error -> {
//                            Text("Error")
//                        }
//                    }
//                }

                // Section for muscle categories
                viewModel.muscleTargetState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            ListSection(
                                title = stringResource(id = R.string.section_discover_title),
                                subtitle = stringResource(id = R.string.section_discover_subtitle)
                            ) {

                            }
                            viewModel.fetchListMuscle()
                        }
                        is UiState.Success -> {
                                ListSection(
                                    title = stringResource(id = R.string.section_discover_title),
                                    subtitle = stringResource(id = R.string.section_discover_subtitle)
                                ) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                    ) {
                                        items(uiState.data) { muscle ->
                                            Chip(
                                                id = muscle.id,
                                                value = muscle.name,
                                                isActive = activeMuscleId == muscle.id,
                                                onChipClick = { viewModel.setActiveMuscleId(muscle.id) }
                                            )
                                        }
                                    }
                                }
                            }
                        is UiState.Error -> {
                            Text(stringResource(id = R.string.error_message))
                        }
                    }
                }

                // Section for exercises based on muscle category
                viewModel.discoverExerciseState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {

                            activeMuscleId?.let { viewModel.fetchWorkoutListByIdMuscle(it) }
                        }
                        is UiState.Success -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(155.dp),
                                    contentPadding = PaddingValues(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.height(400.dp)
                                ) {
                                    items(uiState.data) { exercise ->
                                        ExerciseGridCard(exercise = exercise, navigateToDetail)
                                    }
                                }

                        }
                        is UiState.Error -> {
                            Text(stringResource(id = R.string.error_message))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Chip(
    id: Int,
    value: String,
    isActive: Boolean,
    onChipClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    val contentColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

    Card(

        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.copy(alpha = 0.1F),
            contentColor = contentColor,

            ),
        shape = RoundedCornerShape(50.dp),
        modifier = modifier.clickable {
            onChipClick(id)
        }
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                )
        }


    }
}

