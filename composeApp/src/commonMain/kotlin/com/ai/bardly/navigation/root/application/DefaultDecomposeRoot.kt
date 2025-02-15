package com.ai.bardly.navigation.root.application

import com.ai.bardly.navigation.root.main.DefaultMainComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.essenty.backhandler.BackHandlerOwner

class DefaultDecomposeRoot(
    componentContext: ComponentContext,
) : DecomposeRoot, ComponentContext by componentContext, BackHandlerOwner {

    private val navigation = StackNavigation<DecomposeRoot.RootConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = DecomposeRoot.RootConfig.serializer(),
        initialStack = { listOf(DecomposeRoot.RootConfig.Main) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun childFactory(
        screenConfig: DecomposeRoot.RootConfig,
        componentContext: ComponentContext
    ): DecomposeRoot.ApplicationChild = when (screenConfig) {
        DecomposeRoot.RootConfig.Main -> DecomposeRoot.ApplicationChild.Main(
            DefaultMainComponent(
                componentContext
            )
        )
    }
}
