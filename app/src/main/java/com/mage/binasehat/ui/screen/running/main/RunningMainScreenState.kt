package com.mage.binasehat.ui.screen.running.main

import com.mage.binasehat.data.model.Run
import com.mage.binasehat.domain.model.CurrentRunStateWithCalories

data class RunningMainScreenState (
    val runList: List<Run> = emptyList(),
    val currentRunStateWithCalories: CurrentRunStateWithCalories = CurrentRunStateWithCalories(),
    val currentRunInfo: Run? = null,
    val distanceCoveredInKmInThisWeek: Float = 0.0f,
    val totalDistanceInKm: Float = 0f,
    val totalDurationInHr: Float = 0f,
    val totalCaloriesBurnt: Long = 0,
    val isEditMode: Boolean = false,
    val errorMsg: String? = null
)