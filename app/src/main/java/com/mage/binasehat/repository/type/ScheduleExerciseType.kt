package com.mage.binasehat.repository.type

import com.mage.binasehat.data.local.entity.ScheduleExerciseEntity
import com.mage.binasehat.data.model.ScheduleExercise
import kotlinx.coroutines.flow.Flow

interface ScheduleExerciseType {

    suspend fun insertSchedule(exercise: ScheduleExerciseEntity)
    fun getAllSchedule() : Flow<List<ScheduleExercise>>


    fun isExerciseAlreadyExist(date : String, exerciseId : Long) : List<ScheduleExerciseEntity>
    fun getAllExerciseByDate(date : String) : Flow<List<ScheduleExerciseEntity>>

    suspend fun updateExerciseSchedule(workoutId: Long, dateString: String)

    suspend fun deleteScheduleByDate(date: String)
    suspend fun deleteExercise(exercise: ScheduleExerciseEntity)
}