package com.retro99.snackbar.api

import com.retro99.base.ui.compose.TextWrapper

data class SnackbarData(
    val title: TextWrapper,
    val durationMillis: Long? = 3000,
    val action: (() -> Unit)? = null,
)
