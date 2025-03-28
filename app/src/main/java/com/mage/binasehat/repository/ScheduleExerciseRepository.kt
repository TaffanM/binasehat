package com.mage.binasehat.repository

import android.annotation.SuppressLint
import com.mage.binasehat.data.local.db.dao.ScheduleExerciseDao
import com.mage.binasehat.data.local.entity.ScheduleExerciseEntity
import com.mage.binasehat.data.model.ScheduleExercise
import com.mage.binasehat.data.model.TodayExerciseSummary
import com.mage.binasehat.repository.type.ScheduleExerciseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class ScheduleExerciseRepository @Inject constructor(private val db: ScheduleExerciseDao) :
    ScheduleExerciseType {
    override suspend fun insertSchedule(exercise: ScheduleExerciseEntity) {
        db.insertExercise(exercise)
    }

    override fun isExerciseAlreadyExist(
        date: String,
        exerciseId: Long
    ) : List<ScheduleExerciseEntity> {
        return db.isExerciseAlreadyExist(date, exerciseId)
    }

    override fun getAllSchedule(): Flow<List<ScheduleExercise>> {
        return flowOf(db.getSummaryByDate())
    }

    override fun getAllExerciseByDate(date: String): Flow<List<ScheduleExerciseEntity>> {
        return flowOf(db.getExercisesByDate(date))
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodaySchedule() : List<ScheduleExerciseEntity> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        return db.getExercisesByDate(currentDate)
    }


    @SuppressLint("SimpleDateFormat")
    fun getTodayExerciseSummary() : Flow<TodayExerciseSummary> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        return flowOf(db.getExerciseSummaryByDate(currentDate))
    }


    override suspend fun deleteScheduleByDate(date: String) {
        db.deleteExerciseByDate(date)
    }

    override suspend fun updateExerciseSchedule(workoutId: Long, dateString: String) {
        db.updateExerciseSchedule(workoutId, dateString)
    }

    override suspend fun deleteExercise(exercise: ScheduleExerciseEntity) {
        db.deleteExercise(exercise)
    }
}