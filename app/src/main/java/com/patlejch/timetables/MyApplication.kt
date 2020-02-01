package com.patlejch.timetables

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.facebook.stetho.Stetho
import com.patlejch.timetables.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("ConstantConditionIf")
class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Kotpref.init(this)

        startKoin {
            androidContext(this@MyApplication)
            modules(koinModules)
        }

        if (Constants.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        if (Constants.DEBUG) {
            Timber.plant(debugTree)
        } else {
            Timber.plant(crashReportingTree)
        }
    }

    private val debugTree = Timber.DebugTree()

    private val crashReportingTree = object : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            // todo add crash reporting
        }
    }
}
