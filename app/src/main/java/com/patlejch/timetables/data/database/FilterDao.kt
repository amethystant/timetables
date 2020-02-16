package com.patlejch.timetables.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.patlejch.timetables.model.entity.internal.Filter
import com.skoumal.teanity.database.BaseDao

@Dao
interface FilterDao : BaseDao<Filter> {

    @Query("select * from filter")
    suspend fun fetchAll(): List<Filter>

    @Query("delete from filter")
    suspend fun delete()

    @Transaction
    suspend fun overwriteList(list: List<Filter>) {
        delete()
        insert(list)
    }
}
