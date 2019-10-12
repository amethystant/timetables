package com.patlejch.timetables.util

import com.patlejch.timetables.Config

private val Int.string get() = Config.context.getString(this)