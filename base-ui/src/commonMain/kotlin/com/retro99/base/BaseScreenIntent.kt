package com.retro99.base

import kotlin.jvm.JvmInline

interface BaseScreenIntent

@JvmInline
value class IntentDispatcher<T : BaseScreenIntent>(private val handler: (T) -> Unit) {
    operator fun invoke(intent: T) {
        handler(intent)
    }
}
