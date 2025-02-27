package com.retro99.snackbar.api

import kotlinx.coroutines.flow.SharedFlow

interface SnackbarManager {
    val messages: SharedFlow<SnackbarData>
    fun showSnackbar(snackbarData: SnackbarData)
}
