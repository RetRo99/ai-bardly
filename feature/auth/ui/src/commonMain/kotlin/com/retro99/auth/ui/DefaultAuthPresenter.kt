package com.retro99.auth.ui

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.retro99.auth.ui.login.LoginMode
import com.retro99.auth.ui.login.LoginPresenterFactory
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias AuthPresenterFactory = (
    ComponentContext,
    onLoginSuccess: () -> Unit,
) -> DefaultAuthPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = AuthPresenter::class)
class DefaultAuthPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onLoginSuccess: () -> Unit,
    private val loginPresenterFactory: LoginPresenterFactory,
) : AuthPresenter, ComponentContext by componentContext {
    private val navigation = StackNavigation<AuthPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = AuthPresenter.Config.serializer(),
        handleBackButton = true,
        initialConfiguration = AuthPresenter.Config.SignIn,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: AuthPresenter.Config) {
        navigation.pushNew(config)
    }

    private fun openSignUp() {
        navigation.pushNew(AuthPresenter.Config.SignUp)
    }

    private fun childFactory(
        screenConfig: AuthPresenter.Config,
        componentContext: ComponentContext
    ): AuthPresenter.Child = when (screenConfig) {
        AuthPresenter.Config.SignIn -> AuthPresenter.Child.SignIn(
            loginPresenterFactory(
                componentContext,
                LoginMode.SignIn,
                ::openSignUp,
                onLoginSuccess
            )
        )

        AuthPresenter.Config.SignUp -> AuthPresenter.Child.SignUp(
            loginPresenterFactory(
                componentContext,
                LoginMode.SignUp,
                {},
                onLoginSuccess
            )
        )
    }
}