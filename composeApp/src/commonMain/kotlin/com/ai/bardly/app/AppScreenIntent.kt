package com.ai.bardly.app

import com.retro99.base.ui.BaseScreenIntent

sealed interface AppScreenIntent : BaseScreenIntent {
    data object SnackbarMessageShown : AppScreenIntent
}