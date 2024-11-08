package com.mage.binasehat.repository

import com.mage.binasehat.data.local.fake.FakeData
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.remote.response.DetailExercise
import com.mage.binasehat.data.remote.response.DetailExerciseResponse
import com.mage.binasehat.data.remote.response.ExerciseData
import com.mage.binasehat.data.remote.response.ExerciseResponse
import com.mage.binasehat.data.remote.response.MuscleResponse
import com.mage.binasehat.data.remote.response.MuscleTarget
import com.mage.binasehat.data.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor() {

    private val exercises = mutableListOf<Exercise>()

    init {
        if (exercises.isEmpty()) {
            FakeData.fakeExerciseData.forEach { exercise ->
                exercises.add(
                    Exercise(
                        exercise.id,
                        exercise.name,
                        exercise.rating,
                        exercise.level,
                        exercise.calEstimation,
                        exercise.requiredEquiment,
                        exercise.explain,
                        exercise.step,
                        exercise.category,
                        exercise.isSupportInteractive,
                        exercise.interactiveSetting,
                        exercise.interctiveBodyPartSegmentValue,
                        exercise.bodyPartNeeded,
                        exercise.muscle,
                        exercise.photo,
                        exercise.Gif
                    )
                )
            }
        }
    }

    fun getTopExercise(limit: Int = 5): Flow<UiState<ExerciseResponse>> = flow {
        emit(UiState.Loading)
        // Convert `Exercise` objects to `ExerciseData` for compatibility with `ExerciseResponse`
        val topExercises = exercises.sortedByDescending { it.rating }
            .take(limit)
            .map { exercise ->
                ExerciseData(
                    id = exercise.id,
                    name = exercise.name,
                    rating = exercise.rating,
                    level = exercise.level,
                    calEstimation = exercise.calEstimation,
                    requiredEquipment = exercise.requiredEquiment,
                    overview = exercise.explain,
                    category = exercise.category,
                    isSupportInteractive = exercise.isSupportInteractive,
                    gifUrl = exercise.Gif,
                    muscle = exercise.muscle,
                    photoUrl = exercise.photo
                )
            }
        emit(UiState.Success(ExerciseResponse(data = topExercises)))
    }

    fun getWorkoutByIdMuscle(muscleId: Int): Flow<UiState<ExerciseResponse>> = flow {
        emit(UiState.Loading)
        // Filter exercises by muscle ID and convert to `ExerciseData`
        val filteredExercises = exercises.filter { it.muscle?.id == muscleId }
            .map { exercise ->
                ExerciseData(
                    id = exercise.id,
                    name = exercise.name,
                    rating = exercise.rating,
                    level = exercise.level,
                    calEstimation = exercise.calEstimation,
                    requiredEquipment = exercise.requiredEquiment,
                    overview = exercise.explain,
                    category = exercise.category,
                    isSupportInteractive = exercise.isSupportInteractive,
                    gifUrl = exercise.Gif,
                    muscle = exercise.muscle,
                    photoUrl = exercise.photo
                )
            }
        emit(UiState.Success(ExerciseResponse(data = filteredExercises)))
    }

    fun getAllMuscleCategory(): Flow<UiState<MuscleResponse>> = flow {
        emit(UiState.Loading)
        // Map fake muscle data to `MuscleTarget`
        val muscleCategories = FakeData.fakeMuscleData.map { muscle ->
            MuscleTarget(
                id = muscle.id,
                name = muscle.name
            )
        }
        emit(UiState.Success(MuscleResponse(data = muscleCategories)))
    }

    fun getWorkoutById(workoutId: Long): Flow<UiState<DetailExerciseResponse>> = flow {
        emit(UiState.Loading)
        // Find exercise by ID and map to `DetailExercise`
        val exercise = exercises.find { it.id == workoutId }?.let {
            DetailExercise(
                id = it.id,
                name = it.name,
                rating = it.rating,
                level = it.level,
                calEstimation = it.calEstimation,
                requiredEquipment = it.requiredEquiment,
                overview = it.explain,
                step = it.step,
                category = it.category,
                isSupportInteractive = it.isSupportInteractive,
                gifUrl = it.Gif,
                muscle = it.muscle,
                photoUrl = it.photo,
                interactiveSetting = it.interactiveSetting,
                interactiveBodyPartSegmentValue = it.interctiveBodyPartSegmentValue,
                bodyPartNeeded = it.bodyPartNeeded
            )
        }
        if (exercise != null) {
            emit(UiState.Success(DetailExerciseResponse(data = exercise)))
        } else {
            emit(UiState.Error("Exercise not found"))
        }
    }

    fun searchExercise(query: String): Flow<UiState<ExerciseResponse>> = flow {
        emit(UiState.Loading)
        // Filter exercises by query and convert to `ExerciseData`
        val filteredExercises = exercises.filter { it.name.contains(query, ignoreCase = true) }
            .map { exercise ->
                ExerciseData(
                    id = exercise.id,
                    name = exercise.name,
                    rating = exercise.rating,
                    level = exercise.level,
                    calEstimation = exercise.calEstimation,
                    requiredEquipment = exercise.requiredEquiment,
                    overview = exercise.explain,
                    category = exercise.category,
                    isSupportInteractive = exercise.isSupportInteractive,
                    gifUrl = exercise.Gif.toString(),
                    muscle = exercise.muscle,
                    photoUrl = exercise.photo.toString()
                )
            }
        emit(UiState.Success(ExerciseResponse(data = filteredExercises)))
    }
}