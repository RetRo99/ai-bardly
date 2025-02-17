package com.ai.bardly.state

import com.arkivanov.essenty.statekeeper.SerializableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import platform.Foundation.NSCoder
import platform.Foundation.NSString
import platform.Foundation.decodeTopLevelObjectOfClass
import platform.Foundation.encodeObject

typealias stateKeeperSaveState = (coder: NSCoder, state: SerializableContainer) -> Unit

@Suppress("unused") // Used in Swift
@Inject
fun stateKeeperSaveState(
    @Assisted coder: NSCoder,
    @Assisted state: SerializableContainer,
    json: Json,
) {
    coder.encodeObject(
        `object` = json.encodeToString(SerializableContainer.serializer(), state),
        forKey = "state"
    )
}

typealias stateKeeperRestoreState = (coder: NSCoder) -> StateKeeperDispatcher

@Suppress("unused") // Used in Swift
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Inject
fun stateKeeperRestoreState(@Assisted coder: NSCoder, json: Json): StateKeeperDispatcher {
    return (coder.decodeTopLevelObjectOfClass(
        aClass = NSString,
        forKey = "state",
        error = null
    ) as String?)?.let {
        try {
            json.decodeFromString(SerializableContainer.serializer(), it)
        } catch (e: Exception) {
            null
        }
    } as StateKeeperDispatcher
}
