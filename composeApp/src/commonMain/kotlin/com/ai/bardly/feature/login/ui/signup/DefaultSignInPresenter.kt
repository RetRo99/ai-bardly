package com.ai.bardly.feature.login.ui.signup

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.login.ui.components.InputValidator
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
    private val inputValidator: InputValidator,
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
                emailField = it.emailField.copy(
                    value = newEmailInput,
                    validator = inputValidator
                )
            )
        }
    }

    private fun updatePasswordInput(newPasswordInput: String) {
        updateOrSetSuccess {
            it.copy(
                passwordField = it.passwordField.copy(
                    value = newPasswordInput,
                    validator = inputValidator
                )
            )
        }
    }

    private fun togglePasswordVisibility(isVisible: Boolean) {
        updateOrSetSuccess {
            it.copy(
                passwordField = it.passwordField.copy(
                    isPasswordVisible = isVisible,
                    validator = inputValidator,
                )
            )
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        TODO("Not yet implemented")
    }

    private fun handleSignInWithGoogleResult(result: Result<FirebaseUser?>) {
        TODO("Not yet implemented")
    }
}