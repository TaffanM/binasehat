package com.mage.binasehat.repository

import com.mage.binasehat.data.local.fake.FakeData
import com.mage.binasehat.data.model.Exercise
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


}