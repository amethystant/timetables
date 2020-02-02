package com.patlejch.timetables.ui.settings

import android.animation.LayoutTransition
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentSettingsBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.util.Insets
import com.skoumal.teanity.viewevents.ViewEvent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : TimetablesFragment<SettingsViewModel, FragmentSettingsBinding>() {

    override val layoutRes = R.layout.fragment_settings

    override val viewModel: SettingsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingsLayoutAnimated.layoutTransition?.enableTransitionType(LayoutTransition.CHANGING)
    }

    override fun onEventDispatched(event: ViewEvent) {
        super.onEventDispatched(event)
        if (event is ViewEvents.ShowTimePicker) {
            showTimePicker(event)
        }
    }

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(top = top)

    private fun showTimePicker(event: ViewEvents.ShowTimePicker) {
        TimePickerDialog(
            requireContext(),
            event.listener,
            event.hour,
            event.minute,
            DateFormat.is24HourFormat(requireContext())
        ).show()
    }
}