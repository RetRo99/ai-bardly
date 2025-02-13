package com.ai.bardly.navigation.root.application

import com.ai.bardly.navigation.root.main.DefaultMainComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.essenty.backhandler.BackHandlerOwner

class DefaultApplicationComponent(
    componentContext: ComponentContext,
) : ApplicationComponent, ComponentContext by componentContext, BackHandlerOwner {

    private val navigation = StackNavigation<ApplicationComponent.RootConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = ApplicationComponent.RootConfig.serializer(),
        initialStack = { listOf(ApplicationComponent.RootConfig.Main) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun childFactory(
        screenConfig: ApplicationComponent.RootConfig,
        componentContext: ComponentContext
    ): ApplicationComponent.ApplicationChild = when (screenConfig) {
        ApplicationComponent.RootConfig.Main -> ApplicationComponent.ApplicationChild.Main(
            DefaultMainComponent(
                componentContext
            )
        )
    }
}
