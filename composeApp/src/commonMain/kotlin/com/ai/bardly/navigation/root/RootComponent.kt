package com.ai.bardly.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner

interface RootComponent<T : Any> : BackHandlerOwner {
    val childStack: Value<ChildStack<*, T>>
    fun onBackClicked()
}