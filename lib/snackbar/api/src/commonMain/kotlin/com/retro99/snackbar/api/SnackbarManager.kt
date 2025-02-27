package com.retro99.snackbar.api

import com.retro99.base.ui.compose.TextWrapper
import kotlinx.coroutines.flow.SharedFlow

interface SnackbarManager {
    val messages: SharedFlow<SnackbarData>
    fun showSnackbar(snackbarData: SnackbarData)
    fun showSnackbar(title: TextWrapper)
}
