package com.mage.binasehat.ui.screen.workout.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.model.Muscle
import com.mage.binasehat.data.model.TodayExerciseSummary
import com.mage.binasehat.data.remote.response.ExerciseResponse
import com.mage.binasehat.data.remote.response.MuscleResponse
import com.mage.binasehat.data.util.UiState
import com.mage.binasehat.repository.ExerciseRepository
import com.mage.binasehat.repository.ScheduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutMainViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository, private val scheduleExerciseRepository: ScheduleExerciseRepository) : ViewModel() {

    private val _exercisePopular: MutableStateFlow<UiState<List<Exercise>>> = MutableStateFlow(UiState.Loading)
    val exercisePopular: StateFlow<UiState<List<Exercise>>>
        get() = _exercisePopular

    private val _discoverExerciseState: MutableStateFlow<UiState<List<Exercise>>> = MutableStateFlow(UiState.Loading)
    val discoverExerciseState: StateFlow<UiState<List<Exercise>>>
        get() = _discoverExerciseState

    private val _muscleTargetState: MutableStateFlow<UiState<List<Muscle>>> = MutableStateFlow(UiState.Loading)
    val muscleTargetState: StateFlow<UiState<List<Muscle>>>
        get() = _muscleTargetState

    private val _activeMuscleId: MutableStateFlow<Int?> = MutableStateFlow(1)
    val activeMuscleId: StateFlow<Int?>
        get() = _activeMuscleId

    private val _todaySchedule: MutableStateFlow<UiState<TodayExerciseSummary>> = MutableStateFlow(UiState.Loading)
    val todaySchedule: StateFlow<UiState<TodayExerciseSummary>>
        get() = _todaySchedule

    init {
        fetchPopularWorkout()
        fetchListMuscle()
    }

    fun fetchPopularWorkout() {
        viewModelScope.launch {
            exerciseRepository.getTopExercise().catch { exception ->
                _exercisePopular.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercises ->
                _exercisePopular.value = exercises
            }
        }
    }

    fun fetchWorkoutListByIdMuscle(muscleId: Int) {
        viewModelScope.launch {
            exerciseRepository.getWorkoutByIdMuscle(muscleId).catch { exception ->
                _discoverExerciseState.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercise ->
                _discoverExerciseState.value = exercise
            }
        }
    }

    fun getTodaySchedule() {
        viewModelScope.launch {
            scheduleExerciseRepository.getTodayExerciseSummary().catch { exception ->
                _todaySchedule.value = UiState.Error(exception.message.orEmpty())
            }.collect { schedule ->
                _todaySchedule.value = UiState.Success(schedule)
            }
        }
    }

    fun fetchListMuscle() {
        viewModelScope.launch {
            exerciseRepository.getAllMuscleCategory().catch { exception ->
                _muscleTargetState.value = UiState.Error(exception.message.orEmpty())
            }.collect { muscles ->
                _muscleTargetState.value = muscles
            }
        }
    }

    fun setActiveMuscleId(muscleId: Int) {
        _discoverExerciseState.value = UiState.Loading
        _activeMuscleId.value = muscleId
        fetchWorkoutListByIdMuscle(muscleId)
    }
}
