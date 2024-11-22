package com.mage.binasehat.ui.screen.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.model.Run
import com.mage.binasehat.data.remote.ApplicationScope
import com.mage.binasehat.data.remote.IoDispatcher
import com.mage.binasehat.domain.tracking.TrackingManager
import com.mage.binasehat.domain.usecase.GetCurrentRunStateWithCaloriesUseCase
import com.mage.binasehat.extension.setDateToWeekFirstDay
import com.mage.binasehat.extension.setDateToWeekLastDay
import com.mage.binasehat.repository.RunRepository
import com.mage.binasehat.ui.screen.running.main.RunningMainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val repository: RunRepository,
    trackingManager: TrackingManager,
    @ApplicationScope
    private val externalScope: CoroutineScope,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    getCurrentRunStateWithCaloriesUseCase: GetCurrentRunStateWithCaloriesUseCase
) : ViewModel() {

    val durationInMillis = trackingManager.trackingDurationInMs


    private val calendar = Calendar.getInstance()

    private val distanceCoveredInThisWeekInMeter = repository.getTotalDistance(
        calendar.setDateToWeekFirstDay().time,
        calendar.setDateToWeekLastDay().time
    )

    private val totalDistance = repository.getTotalDistance()
    private val totalDuration = repository.getTotalRunningDuration()
    private val totalCalories = repository.getTotalCaloriesBurned()

    // Combine total stats separately
    private val totalStats = combine(
        totalDistance,
        totalDuration,
        totalCalories
    ) { distance, duration, calories ->
        Triple(distance, duration, calories)
    }

    private val _homeScreenState = MutableStateFlow(RunningMainScreenState())
    val runningMainScreenState = combine(
        repository.getRunByDescDateWithLimit(3),
        getCurrentRunStateWithCaloriesUseCase(),
        distanceCoveredInThisWeekInMeter,
        totalStats,
        _homeScreenState,
    ) { runList, runState, distanceInMeter, (totalDistanceInMeter, totalDurationInMillis, totalCalories), state ->
        state.copy(
            runList = runList,
            currentRunStateWithCalories = runState,
            distanceCoveredInKmInThisWeek = distanceInMeter / 1000f,
            totalDistanceInKm = totalDistanceInMeter / 1000f,
            totalDurationInHr = totalDurationInMillis / (1000f * 60f * 60f),
            totalCaloriesBurnt = totalCalories
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        RunningMainScreenState()
    )

    fun deleteRun(run: Run) = externalScope.launch(ioDispatcher) {
        dismissRunDialog()
        repository.deleteRun(run)
    }

    fun showRun(run: Run) {
        _homeScreenState.update { it.copy(currentRunInfo = run) }
    }

    fun dismissRunDialog() {
        _homeScreenState.update { it.copy(currentRunInfo = null) }
    }

}