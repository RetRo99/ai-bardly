package com.retro99.base.ui.decompose

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner

interface SlotDecomposeComponent<Child : Any, Config : Any> : BackHandlerOwner {
    val childSlot: Value<ChildSlot<Config, Child>>
    funonBackClicked()
}