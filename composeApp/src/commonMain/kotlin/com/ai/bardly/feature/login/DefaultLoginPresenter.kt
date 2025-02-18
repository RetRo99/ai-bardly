package com.ai.bardly.feature.login

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.decompose.switchTab
import com.ai.bardly.feature.login.ui.SignInPresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias LoginPresenterFactory = (
    ComponentContext,
    openMain: () -> Unit
) -> DefaultLoginPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = LoginPresenter::class)
class DefaultLoginPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openMain: () -> Unit,
    private val signInPresenterFactory: SignInPresenterFactory,
) : LoginPresenter, ComponentContext by componentContext {
    private val navigation = StackNavigation<LoginPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = LoginPresenter.Config.serializer(),
        initialConfiguration = LoginPresenter.Config.SignIn,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: LoginPresenter.Config) {
        navigation.switchTab(config)
    }

    private fun childFactory(
        screenConfig: LoginPresenter.Config,
        componentContext: ComponentContext
    ): LoginPresenter.Child = when (screenConfig) {
        LoginPresenter.Config.SignIn -> LoginPresenter.Child.SignIn(
            signInPresenterFactory(componentContext)
        )
    }
}