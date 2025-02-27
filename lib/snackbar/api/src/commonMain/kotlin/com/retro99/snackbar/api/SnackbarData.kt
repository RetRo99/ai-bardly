package com.retro99.snackbar.api

data class SnackbarData(
    val title: String,
    val durationMillis: Long? = 5000,
    val action: (() -> Unit)? = null,
)
