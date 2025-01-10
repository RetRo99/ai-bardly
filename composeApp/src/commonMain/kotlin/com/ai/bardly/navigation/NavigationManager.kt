package com.ai.bardly.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigationManager {

    private val _destinations = MutableSharedFlow<GeneralDestination>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val destinations: SharedFlow<GeneralDestination> = _destinations

    fun navigate(command: GeneralDestination) {
        _destinations.tryEmit(command)
    }
}