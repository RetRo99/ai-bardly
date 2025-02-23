package com.retro99.base.ui.decompose

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner

interface RootDecomposeComponent<Child : Any, Config : Any> : BackHandlerOwner {
    val childStack: Value<ChildStack<Config, Child>>
    fun onBackClicked()
}