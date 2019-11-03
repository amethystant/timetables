package com.patlejch.timetables.di

import android.content.Context
import android.net.ConnectivityManager
import com.patlejch.timetables.model.global.Static
import org.koin.dsl.module

val miscModule = module {

    single { get<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    single { Static.tableParams }

}
