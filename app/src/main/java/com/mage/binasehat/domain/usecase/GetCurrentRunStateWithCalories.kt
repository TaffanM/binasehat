package com.mage.binasehat.domain.usecase

import com.mage.binasehat.domain.model.CurrentRunStateWithCalories
import com.mage.binasehat.domain.tracking.TrackingManager
import com.mage.binasehat.ui.util.RunUtility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class GetCurrentRunStateWithCaloriesUseCase @Inject constructor(
    private val trackingManager: TrackingManager
) {
    operator fun invoke(): Flow<CurrentRunStateWithCalories> {
        return trackingManager.currentRunState
            .map { runState ->  // Use the map operator to transform the emitted values
                CurrentRunStateWithCalories(
                    currentRunState = runState,
                    caloriesBurnt = RunUtility.calculateCaloriesBurnt(
                        distanceInMeters = runState.distanceInMeters,
                        weightInKg = 70f
                    ).roundToInt()
                )
            }
    }
}