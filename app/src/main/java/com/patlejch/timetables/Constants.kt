package com.patlejch.timetables

object Constants {

    const val DEBUG = BuildConfig.BUILD_TYPE == "debug"
    const val MOCK = BuildConfig.BUILD_TYPE == "mock"
    const val RELEASE = BuildConfig.BUILD_TYPE == "release"

    val IS_TEST_BUILD = BuildConfig.DEBUG

    val API_URL = "https://timetables.patlejch.co.uk"

    const val DB_FORMAT_WHOLE = "yyyyMMdd'T'HHmmss'Z'"
    const val DB_FORMAT_DAY = "yyyyMMdd"
}
