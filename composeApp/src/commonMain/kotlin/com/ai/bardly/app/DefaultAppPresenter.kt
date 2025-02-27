package com.ai.bardly.app

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.onboarding.OnboardingPresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.retro99.auth.ui.AuthPresenterFactory
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.main.MainPresenterFactory
import com.retro99.snackbar.api.SnackbarManager
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, boundType = AppPresenter::class)
class DefaultAppPresenter(
    componentContext: ComponentContext,
    private val mainPresenterFactory: MainPresenterFactory,
    private val onboardingPresenterFactory: OnboardingPresenterFactory,
    private val authPresenterFactory: AuthPresenterFactory,
    private val snackbarManager: SnackbarManager,
) : BasePresenterImpl<AppViewState, AppScreenIntent>(componentContext), AppPresenter {

    override val defaultViewState = AppViewState()
    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onCreate() {
        subscribeToSnackbars()
    }

    override fun handleScreenIntent(intent: AppScreenIntent) {
        when (intent) {
            is AppScreenIntent.SnackbarMessageShown -> snackbarMessageShown()
        }
    }

    private fun snackbarMessageShown() {
        updateOrSetSuccess { currentViewState ->
            currentViewState.copy(snackbarData = null)
        }
    }

    private fun subscribeToSnackbars() {
        scope.launch {
            snackbarManager.messages.collect { message ->
                updateOrSetSuccess { currentViewState ->
                    currentViewState.copy(snackbarData = message)
                }
            }
        }
    }

    private val navigation = StackNavigation<AppPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = AppPresenter.Config.serializer(),
        initialConfiguration = AppPresenter.Config.Main,
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun onLoginSuccess() {
        onBackClicked()
    }

    private fun openLogin() {
        navigation.pushNew(AppPresenter.Config.Auth)
    }

    private fun childFactory(
        screenConfig: AppPresenter.Config,
        componentContext: ComponentContext
    ): AppPresenter.Child = when (screenConfig) {
        AppPresenter.Config.Main -> AppPresenter.Child.Main(
            mainPresenterFactory(
                componentContext,
                ::openLogin,
            )
        )

        AppPresenter.Config.Onboarding -> AppPresenter.Child.Onboarding(
            onboardingPresenterFactory(
                componentContext,
                ::onLoginSuccess,
            )
        )

        AppPresenter.Config.Auth -> AppPresenter.Child.Auth(
            authPresenterFactory(
                componentContext,
                ::onLoginSuccess,
            )
        )
    }
}