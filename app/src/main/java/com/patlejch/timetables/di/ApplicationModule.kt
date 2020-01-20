package com.patlejch.timetables.di

import android.content.Context
import com.patlejch.timetables.Config
import com.skoumal.teanity.rxbus.RxBus
import org.koin.dsl.module

val applicationModule = module {
    single { RxBus() }
    single { get<Context>().resources }
    single { Config(get()) }
}
