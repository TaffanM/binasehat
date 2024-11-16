package com.mage.binasehat.ui.screen.workout.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.local.entity.ScheduleExerciseEntity
import com.mage.binasehat.data.remote.response.DetailExerciseRespone
import com.mage.binasehat.data.util.Result
import com.mage.binasehat.data.util.UiState
import com.mage.binasehat.repository.ExerciseRepository
import com.mage.binasehat.repository.ScheduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailWorkoutViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val scheduleExerciseRepository: ScheduleExerciseRepository
) : ViewModel() {

    private val _exercise = MutableStateFlow<Result<DetailExerciseRespone>>(Result.Loading)
    val exercise: StateFlow<Result<DetailExerciseRespone>> = _exercise.asStateFlow()

    fun getWorkoutById(workoutId: Long) {
        viewModelScope.launch {
            try {
                repository.getWorkoutById(workoutId)
                    .catch { exception ->
                        Log.e("DetailWorkoutViewModel", "Error fetching workout", exception)
                        _exercise.value = Result.Error("Failed to load workout: ${exception.localizedMessage}")
                    }
                    .collect { result ->
                        _exercise.value = result
                    }
            } catch (e: Exception) {
                Log.e("DetailWorkoutViewModel", "Error in getWorkoutById", e)
                _exercise.value = Result.Error("An unexpected error occurred: ${e.localizedMessage}")
            }
        }
    }

    fun addWorkoutSchedule(exerciseSchedule: ScheduleExerciseEntity): String {
        var message = ""
        viewModelScope.launch {
            try {
                val isAlreadyExist = scheduleExerciseRepository.isExerciseAlreadyExist(
                    exerciseSchedule.dateString,
                    exerciseSchedule.id_exercise
                )
                message = if (isAlreadyExist.isNotEmpty()) {
                    "Exercise is already scheduled for this date."
                } else {
                    scheduleExerciseRepository.insertSchedule(exerciseSchedule)
                    "Exercise scheduled successfully."
                }
            } catch (e: Exception) {
                Log.e("DetailWorkoutViewModel", "Error in addWorkoutSchedule", e)
                message = "Failed to schedule exercise: ${e.localizedMessage}"
            }
        }
        return message
    }
}