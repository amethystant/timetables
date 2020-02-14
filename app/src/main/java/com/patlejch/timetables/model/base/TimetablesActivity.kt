package com.patlejch.timetables.model.base

import androidx.databinding.ViewDataBinding
import com.skoumal.teanity.view.TeanityActivity

abstract class TimetablesActivity<ViewModel : TimetablesViewModel, Binding : ViewDataBinding> :
    TeanityActivity<ViewModel, Binding>()