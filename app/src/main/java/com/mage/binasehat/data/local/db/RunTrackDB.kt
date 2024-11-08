package com.mage.binasehat.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mage.binasehat.data.local.db.dao.RunDao
import com.mage.binasehat.data.local.db.mapper.DBConverters
import com.mage.binasehat.data.model.Run

@Database(
    entities = [Run::class],
    version = 1,
)

@TypeConverters(DBConverters::class)
abstract class RunTrackDB : RoomDatabase() {
    companion object {
        const val RUN_TRACK_DB_NAME = "run_track_db"
    }

    abstract fun getRunDao(): RunDao

}