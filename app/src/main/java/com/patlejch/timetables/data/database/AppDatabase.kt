package com.patlejch.timetables.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.patlejch.timetables.model.entity.internal.DbEntity

@Database(
    version = 1,
    entities = [
        DbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "database"
    }

    abstract fun dbEntityDao(): DbEntityDao // todo delete this example entity & dao

}
