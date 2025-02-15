package com.ai.bardly.navigation.root.application

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.navigation.root.main.MainPresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, boundType = RootPresenter::class)
class DefaultRootPresenter(
    componentContext: ComponentContext,
    private val mainPresenterFactory: MainPresenterFactory,
) : RootPresenter, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootPresenter.RootConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootPresenter.RootConfig.serializer(),
        initialConfiguration = RootPresenter.RootConfig.Main,
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun childFactory(
        screenConfig: RootPresenter.RootConfig,
        componentContext: ComponentContext
    ): RootPresenter.ApplicationChild = when (screenConfig) {
        RootPresenter.RootConfig.Main -> RootPresenter.ApplicationChild.Main(
            mainPresenterFactory(
                componentContext
            )
        )
    }
}
