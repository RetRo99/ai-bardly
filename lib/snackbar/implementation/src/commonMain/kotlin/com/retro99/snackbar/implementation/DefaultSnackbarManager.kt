package com.retro99.snackbar.implementation

import com.retro99.snackbar.api.SnackbarData
import com.retro99.snackbar.api.SnackbarManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultSnackbarManager : SnackbarManager {

    private val _messages = MutableSharedFlow<SnackbarData>(extraBufferCapacity = 10)
    override val messages = _messages.asSharedFlow()

    override fun showSnackbar(snackbarData: SnackbarData) {
        _messages.tryEmit(snackbarData)
    }

}