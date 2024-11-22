package com.mage.binasehat.ui.screen.running.current

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.model.Run
import com.mage.binasehat.data.remote.ApplicationScope
import com.mage.binasehat.data.remote.IoDispatcher
import com.mage.binasehat.data.remote.model.RunSubmissionRequest
import com.mage.binasehat.data.remote.response.RunningResponse
import com.mage.binasehat.domain.model.CurrentRunStateWithCalories
import com.mage.binasehat.domain.tracking.TrackingManager
import com.mage.binasehat.domain.usecase.GetCurrentRunStateWithCaloriesUseCase
import com.mage.binasehat.repository.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CurrentRunViewModel @Inject constructor(
    private val trackingManager: TrackingManager,
    private val repository: RunRepository,
    @ApplicationScope
    private val appCoroutineScope: CoroutineScope,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    getCurrentRunStateWithCaloriesUseCase: GetCurrentRunStateWithCaloriesUseCase
) : ViewModel() {
    val currentRunStateWithCalories = getCurrentRunStateWithCaloriesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CurrentRunStateWithCalories()
        )
    val runningDurationInMillis = trackingManager.trackingDurationInMs

    private val _uploadResult = MutableLiveData<Result<RunningResponse>>()
    val uploadResult: LiveData<Result<RunningResponse>> = _uploadResult


    fun playPauseTracking() {
        if (currentRunStateWithCalories.value.currentRunState.isTracking)
            trackingManager.pauseTracking()
        else trackingManager.startResumeTracking()
    }

    fun finishRun(bitmap: Bitmap) {
        trackingManager.pauseTracking()
        val run =  Run(
            img = bitmap,
            avgSpeedInKMH = currentRunStateWithCalories.value.currentRunState.distanceInMeters
                .toBigDecimal()
                .multiply(3600.toBigDecimal())
                .divide(runningDurationInMillis.value.toBigDecimal(), 2, RoundingMode.HALF_UP)
                .toFloat(),
            distanceInMeters = currentRunStateWithCalories.value.currentRunState.distanceInMeters,
            durationInMillis = runningDurationInMillis.value,
            timestamp = Date(),
            caloriesBurned = currentRunStateWithCalories.value.caloriesBurnt
        )
        saveRun(run)
        uploadRunData(run)
        trackingManager.stop()
    }

    private fun saveRun(run: Run) = appCoroutineScope.launch(ioDispatcher) {
        repository.insertRun(run)
    }

    private fun uploadRunData(run: Run) = appCoroutineScope.launch(ioDispatcher) {
        val runSubmissionRequest = RunSubmissionRequest(
            distance = run.distanceInMeters.toFloat(),
            duration = (run.durationInMillis / 1000).toInt(),
            calories_burned = run.caloriesBurned.toFloat()
        )
        repository.submitRun(runSubmissionRequest)
    }

}