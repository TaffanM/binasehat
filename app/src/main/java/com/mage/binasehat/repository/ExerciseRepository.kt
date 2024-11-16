package com.mage.binasehat.repository

import com.mage.binasehat.data.local.fake.FakeData
import com.mage.binasehat.data.model.Exercise
import com.mage.binasehat.data.model.Muscle
import com.mage.binasehat.data.remote.response.DetailExercise
import com.mage.binasehat.data.remote.response.DetailExerciseRespone
import com.mage.binasehat.data.remote.response.InteractiveBodyPartSegmentValue
import com.mage.binasehat.data.remote.response.InteractiveSetting
import com.mage.binasehat.data.util.Result
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

    fun getTopExercise(limit: Int = 5): Flow<UiState<List<Exercise>>> = flow {
        emit(UiState.Loading)
        try {
            val topExercises = exercises.sortedByDescending { it.rating }.take(limit)
            emit(UiState.Success(topExercises))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to load top exercises"))
        }
    }

    fun getWorkoutByIdMuscle(muscleId: Int): Flow<UiState<List<Exercise>>> = flow {
        emit(UiState.Loading)
        try {
            val muscleExercises = exercises.filter { it.muscle.id == muscleId }
            emit(UiState.Success(muscleExercises))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to load exercises for muscle ID: $muscleId"))
        }
    }

    fun getAllMuscleCategory(): Flow<UiState<List<Muscle>>> = flow {
        emit(UiState.Loading)
        try {
            val muscles = exercises.map { it.muscle }.distinct()
            emit(UiState.Success(muscles))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to load muscle categories"))
        }
    }

    fun getWorkoutById(workoutId: Long): Flow<Result<DetailExerciseRespone>> = flow {
        emit(Result.Loading)
        try {
            val workout = exercises.find { it.id == workoutId }
            if (workout != null) {
                val response = DetailExerciseRespone(
                    code = 200,
                    data = DetailExercise(
                        id = workout.id.toInt(),
                        name = workout.name,
                        rating = workout.rating,
                        level = workout.level,
                        calEstimation = workout.calEstimation,
                        requiredEquipment = if (workout.requiredEquiment) 1 else 0,
                        overview = workout.explain,
                        step = workout.step.joinToString("\n"),
                        category = workout.category,
                        isSupportInteractive = if (workout.isSupportInteractive) 1 else 0,
                        interactiveSetting = InteractiveSetting(
                            repetition = workout.interactiveSetting.repetion,
                            set = workout.interactiveSetting.set,
                            restInterval = workout.interactiveSetting.RestInterval.toInt()
                        ),
                        interactiveBodyPartSegmentValue = InteractiveBodyPartSegmentValue(
                            rightArm = workout.interctiveBodyPartSegmentValue.rightArm.toInt(),
                            leftArm = workout.interctiveBodyPartSegmentValue.leftArm.toInt(),
                            rightLeg = workout.interctiveBodyPartSegmentValue.rightLeg.toInt(),
                            leftLeg = workout.interctiveBodyPartSegmentValue.leftLeg.toInt()
                        ),
                        bodyPartNeeded = workout.bodyPartNeeded.toList(),
                        muscle = workout.muscle,
                        photoUrl = workout.photo, // You'll need to implement this
                        gifUrl = workout.Gif      // You'll need to implement this
                    ),
                    message = "Success",
                    status = "success"
                )
                emit(Result.Success(response))
            } else {
                emit(Result.Error("Exercise not found"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to load workout by ID: ${e.message}"))
        }
    }



    fun searchExercise(query: String): Flow<UiState<List<Exercise>>> = flow {
        emit(UiState.Loading)
        try {
            val searchResults = exercises.filter { it.name.contains(query, ignoreCase = true) }
            emit(UiState.Success(searchResults))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to search exercises"))
        }
    }
}
