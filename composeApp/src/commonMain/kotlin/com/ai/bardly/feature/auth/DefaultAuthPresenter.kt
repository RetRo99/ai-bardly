package com.ai.bardly.feature.auth

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.auth.ui.login.LoginPresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias AuthPresenterFactory = (
    ComponentContext,
    openMain: () -> Unit
) -> DefaultAuthPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = AuthPresenter::class)
class DefaultAuthPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openMain: () -> Unit,
    private val loginPresenterFactory: LoginPresenterFactory,
) : AuthPresenter, ComponentContext by componentContext {
    private val navigation = StackNavigation<AuthPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = AuthPresenter.Config.serializer(),
        initialConfiguration = AuthPresenter.Config.SignIn,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: AuthPresenter.Config) {
        navigation.pushNew(config)
    }

    fun openSignUp() {
//        navigation
    }

    private fun childFactory(
        screenConfig: AuthPresenter.Config,
        componentContext: ComponentContext
    ): AuthPresenter.Child = when (screenConfig) {
        AuthPresenter.Config.SignIn -> AuthPresenter.Child.SignIn(
            loginPresenterFactory(
                componentContext,
                ::openSignUp
            )
        )
    }
}