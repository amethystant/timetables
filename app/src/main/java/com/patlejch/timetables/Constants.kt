package com.patlejch.timetables

object Constants {

    const val DEBUG = BuildConfig.BUILD_TYPE == "debug"
    const val ALPHA = BuildConfig.BUILD_TYPE == "alpha"
    const val BETA = BuildConfig.BUILD_TYPE == "beta"
    const val RELEASE = BuildConfig.BUILD_TYPE == "release"

    // todo change back to https once you get your CA set up
    val API_URL = "http://timetables.patlejch.co.uk"

    const val DB_FORMAT_WHOLE = "yyyyMMdd'T'HHmmss'Z'"
    const val DB_FORMAT_DAY = "yyyyMMdd"
}
