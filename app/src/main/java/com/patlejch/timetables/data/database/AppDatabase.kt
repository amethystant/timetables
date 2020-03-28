package com.patlejch.timetables.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.model.entity.internal.Filter

@Database(
    version = 2,
    entities = [
        Event::class,
        Filter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "database"
    }

    abstract fun filterDao(): FilterDao
    abstract fun eventDao(): EventDao

}
