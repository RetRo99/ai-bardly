package com.ai.bardly.feature.login.ui

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.login.InputValidator
import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias SignInPresenterFactory = (
    ComponentContext,
) -> DefaultSignInPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = SignInPresenter::class)
class DefaultSignInPresenter(
    @Assisted componentContext: ComponentContext,
    private val analytics: Analytics,
) : BasePresenterImpl<SignInViewState, SignInIntent>(componentContext), SignInPresenter {

    override val defaultViewState = SignInViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.EmailInputChange -> updateEmailInput(intent.email)

            is SignInIntent.PasswordInputChange -> updatePasswordInput(intent.password)

            is SignInIntent.TogglePasswordVisibility -> togglePasswordVisibility(intent.isVisible)

            is SignInIntent.SignInWithEmail -> signInWithEmail(intent.email, intent.password)

            is SignInIntent.SignInWithGoogleResult -> handleSignInWithGoogleResult(intent.result)
        }
    }

    private fun updateEmailInput(newEmailInput: String) {
        updateOrSetSuccess {
            it.copy(
                email = newEmailInput,
                isValidEmail = InputValidator.isValidEmail(newEmailInput)
            )
        }
    }

    private fun updatePasswordInput(newPasswordInput: String) {
        updateOrSetSuccess {
            it.copy(
                password = newPasswordInput,
                isValidPassword = InputValidator.isValidPassword(newPasswordInput)
            )
        }
    }

    private fun togglePasswordVisibility(currentVisibility: Boolean) {
        updateOrSetSuccess {
            it.copy(isPasswordVisible = !currentVisibility)
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        TODO("Not yet implemented")
    }

    private fun handleSignInWithGoogleResult(result: Result<FirebaseUser?>) {
        TODO("Not yet implemented")
    }
}