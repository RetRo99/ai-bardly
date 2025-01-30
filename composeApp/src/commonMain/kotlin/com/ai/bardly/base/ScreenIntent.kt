package com.ai.bardly.base

import kotlin.jvm.JvmInline

interface ScreenIntent

@JvmInline
value class IntentDispatcher<T : ScreenIntent>(private val handler: (T) -> Unit) {
    operator fun invoke(intent: T) {
        handler(intent)
    }
}
