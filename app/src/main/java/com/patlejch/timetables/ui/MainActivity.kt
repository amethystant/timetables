package com.patlejch.timetables.ui

import android.os.Bundle
import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.ActivityMainBinding
import com.patlejch.timetables.model.base.TimetablesActivity
import com.patlejch.timetables.model.navigation.Navigation
import com.patlejch.timetables.util.setupWith
import com.skoumal.teanity.view.TeanityActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : TimetablesActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutRes: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()
    override val navHostId = R.id.main_nav_host

    private val config: Config by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (config.isUrlSet().not()) {
            onEventDispatched(Navigation.setup())
        }

        binding.bottomNavView.setupWith(navController)
    }
}
