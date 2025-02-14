package com.ai.bardly.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigator
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner

interface RootComponent<T : Any> : BackHandlerOwner {
    val childStack: Value<ChildStack<*, T>>
    fun onBackClicked()


    fun <C : Any> StackNavigator<C>.switchTab(configuration: C, onComplete: () -> Unit = {}) {
        navigate(
            transformer = { stack ->
                val existing = stack.find { it::class == configuration::class }
                if (existing != null) {
                    stack.filterNot { it::class == configuration::class } + existing
                } else {
                    stack + configuration
                }
            },
            onComplete = { _, _ -> onComplete() },
        )
    }

}
