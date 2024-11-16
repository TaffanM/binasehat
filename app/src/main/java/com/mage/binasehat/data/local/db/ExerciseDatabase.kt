package com.mage.binasehat.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mage.binasehat.data.local.db.dao.ScheduleExerciseDao
import com.mage.binasehat.data.local.entity.ScheduleExerciseEntity

@Database(entities = [ScheduleExerciseEntity::class], version = 2, exportSchema = false)
abstract class ExerciseDatabase  : RoomDatabase(){

    abstract fun scheduleDao() : ScheduleExerciseDao


}