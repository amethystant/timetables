package com.patlejch.timetables.ui.settings

import android.app.TimePickerDialog
import android.content.Intent
import android.text.format.DateFormat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentSettingsBinding
import com.patlejch.timetables.model.base.AppFragment
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.viewevents.ViewEvent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : AppFragment<SettingsViewModel, FragmentSettingsBinding>() {

    override val layoutRes = R.layout.fragment_settings

    override val viewModel: SettingsViewModel by sharedViewModel()

    override fun onEventDispatched(event: ViewEvent) {
        super.onEventDispatched(event)
        when (event) {
            is ViewEvents.ShowTimePicker -> showTimePicker(event)
            is ViewEvents.OpenOssLicenses -> openOssLicenses()
        }
    }

    private fun showTimePicker(event: ViewEvents.ShowTimePicker) {
        TimePickerDialog(
            requireContext(),
            event.listener,
            event.hour,
            event.minute,
            DateFormat.is24HourFormat(requireContext())
        ).show()
    }

    private fun openOssLicenses() {
        startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
    }
}
