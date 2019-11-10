package com.patlejch.timetables.ui

import android.os.Bundle
import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.ActivityMainBinding
import com.patlejch.timetables.util.setupWith
import com.skoumal.teanity.util.Insets
import com.skoumal.teanity.view.TeanityActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : TeanityActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutRes: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()
    override val navHostId = R.id.main_nav_host

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavView.setupWith(navController)
    }

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(left = left, bottom = bottom)

}
