package com.patlejch.timetables.ui

import android.os.Bundle
import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.data.sync.SyncManager
import com.patlejch.timetables.databinding.ActivityMainBinding
import com.patlejch.timetables.model.base.TimetablesActivity
import com.patlejch.timetables.model.navigation.Navigation
import com.patlejch.timetables.ui.home.HomeViewModel
import com.patlejch.timetables.ui.settings.SettingsViewModel
import com.patlejch.timetables.util.setupWith
import com.skoumal.teanity.util.Insets
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : TimetablesActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutRes: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()
    override val navHostId = R.id.main_nav_host

    private val config: Config by inject()

    private val homeViewModel: HomeViewModel by viewModel()
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val syncManager: SyncManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (config.isUrlSet().not()) {
            onEventDispatched(Navigation.setup())
        }

        syncManager.schedulePeriodicSync()
        viewModel.scheduleNotifications()

        binding.homeViewModel = homeViewModel
        binding.settingsViewModel = settingsViewModel
        binding.bottomNavView.setupWith(navController) {
            viewModel.currentPage.value = if (it == R.id.homeFragment) 0 else 1
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.sync()
    }

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(top = top)

}
