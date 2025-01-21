package com.ai.bardly.base

import kotlin.jvm.JvmInline

interface ScreenIntent

@JvmInline
value class IntentDispatcher<T : ScreenIntent>(val handler: (T) -> Unit) {
    operator fun invoke(intent: T) {
        handler(intent)
    }
}
