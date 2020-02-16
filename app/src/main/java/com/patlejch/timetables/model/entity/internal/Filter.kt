package com.patlejch.timetables.model.entity.internal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter")
data class Filter(@PrimaryKey(autoGenerate = false) val filter: String)