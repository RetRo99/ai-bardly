package com.ai.bardly.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigationManager {

    private val _destinations = MutableSharedFlow<GameDetail>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val destinations: SharedFlow<GameDetail> = _destinations

    fun navigate(command: GameDetail) {
        _destinations.tryEmit(command)
    }
}