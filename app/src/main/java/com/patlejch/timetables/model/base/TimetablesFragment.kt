package com.patlejch.timetables.model.base

import androidx.databinding.ViewDataBinding
import com.skoumal.teanity.view.TeanityFragment

abstract class TimetablesFragment<ViewModel : TimetablesViewModel, Binding : ViewDataBinding> :
    TeanityFragment<ViewModel, Binding>()