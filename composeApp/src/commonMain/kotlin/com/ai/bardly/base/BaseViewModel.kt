package com.ai.bardly.base

import androidx.lifecycle.ViewModel
import com.ai.bardly.analytics.Analytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected val analytics by inject<Analytics>()
}