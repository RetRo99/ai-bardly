package com.ai.bardly.feature.auth.ui.login

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.auth.ui.components.InputValidator
import com.ai.bardly.feature.auth.ui.components.LoginInputField
import com.ai.bardly.user.domain.UserRepository
import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias SignInPresenterFactory = (
    ComponentContext,
) -> DefaultLoginPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = LoginPresenter::class)
class DefaultLoginPresenter(
    @Assisted componentContext: ComponentContext,
    private val analytics: Analytics,
    private val inputValidator: InputValidator,
    private val userRepository: UserRepository,
) : BasePresenterImpl<LoginViewState, LoginIntent>(componentContext), LoginPresenter {

    override val defaultViewState = LoginViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun handleScreenIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailInputChange -> updateEmailInput(intent.email)

            is LoginIntent.PasswordInputChange -> updatePasswordInput(intent.password)

            is LoginIntent.TogglePasswordVisibility -> togglePasswordVisibility(intent.isVisible)

            is LoginIntent.LoginWithEmail -> handleSignInWithEmailClicked(
                intent.email,
                intent.password
            )

            is LoginIntent.LoginWithGoogleResult -> handleSignInWithGoogleResult(intent.result)
        }
    }

    private fun updateEmailInput(newEmailInput: String) {
        updateOrSetSuccess {
            it.copy(
                emailField = it.emailField.copy(
                    value = newEmailInput,
                    showErrorIfNeeded = false,
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
                    showErrorIfNeeded = false,
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

    private fun handleSignInWithEmailClicked(
        email: LoginInputField.Email,
        password: LoginInputField.Password
    ) {
        signInWithEmail(email.value, password.value)
    }

    private fun signInWithEmail(email: String, password: String) {
        scope.launch {
            userRepository.fetchUserWithEmailAndPassword(email, password)
                .onSuccess {
                    it
                }
                .onFailure {
                    updateOrSetSuccess {
                        it.copy(
                            showNoMatchingUserError = true,
                        )
                    }
                }
        }
    }

    private fun handleSignInWithGoogleResult(result: Result<FirebaseUser?>) {
        TODO("Not yet implemented")
    }
}