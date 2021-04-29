package com.patlejch.timetables.di

import android.content.Context
import com.patlejch.timetables.Config
import com.patlejch.timetables.Constants
import com.patlejch.timetables.data.sync.SyncManager
import com.patlejch.timetables.model.notification.NotificationManager
import com.skoumal.teanity.rxbus.RxBus
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DI_DO_MOCK = "DI_DO_MOCK"

val applicationModule = module {
    single { RxBus() }
    single { get<Context>().resources }
    single { Config(get()) }
    single { SyncManager(get()) }
    single { NotificationManager(get()) }
    single(named(DI_DO_MOCK)) { Constants.MOCK }
}
