package com.retro99.auth.ui.login

import UserUiModel
import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.auth.domain.UserRepository
import com.retro99.auth.ui.components.InputValidator
import com.retro99.auth.ui.components.LoginInputField
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.base.ui.compose.TextWrapper
import com.retro99.snackbar.api.SnackbarManager
import com.retro99.translations.StringRes
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import resources.translations.login_sign_in_success
import resources.translations.login_sign_up_success
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import toUiModel

typealias LoginPresenterFactory = (
    ComponentContext,
    loginMode: LoginMode,
    onFooterClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
) -> DefaultLoginPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = LoginPresenter::class)
class DefaultLoginPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val loginMode: LoginMode,
    @Assisted private val onFooterClicked: () -> Unit,
    @Assisted private val onLoginSuccess: () -> Unit,
    private val analytics: Analytics,
    private val inputValidator: InputValidator,
    private val userRepository: UserRepository,
    private val snackbarManager: SnackbarManager,
) : BasePresenterImpl<LoginViewState, LoginIntent>(componentContext), LoginPresenter {

    override val defaultViewState = LoginViewState(loginMode)

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
            is LoginIntent.OnFooterClicked -> onFooterClicked()
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

    private fun onFooterClicked() {
        onFooterClicked.invoke()
    }

    private fun handleSignInWithEmailClicked(
        email: LoginInputField.Email,
        password: LoginInputField.Password
    ) {
        updateOrSetSuccess {
            it.copy(
                showNoMatchingUserError = false,
            )
        }
        when (loginMode) {
            LoginMode.SignUp if email.isValid.not() -> {
                logInvalidSignUpInput(email, password)
                showInputErrors()
            }

            LoginMode.SignUp if password.isValid.not() -> {
                logInvalidSignUpInput(email, password)
                showInputErrors()
            }

            LoginMode.SignUp -> signUpWithEmail(email.value, password.value)
            LoginMode.SignIn -> signInWithEmail(email.value, password.value)
        }
    }

    private fun logInvalidSignUpInput(
        email: LoginInputField.Email,
        password: LoginInputField.Password
    ) {
        if (email.isValid.not()) {
            analytics.log(AnalyticsEvent.SignUpWithEmailInputError(email.state.toString()))
        }
        if (password.isValid.not()) {
            analytics.log(AnalyticsEvent.SignUpWithEmailInputError(password.state.toString()))
        }
    }

    private fun showInputErrors() {
        updateOrSetSuccess {
            it.copy(
                emailField = it.emailField.copy(showErrorIfNeeded = true),
                passwordField = it.passwordField.copy(showErrorIfNeeded = true)
            )
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        launchDataOperation(
            block = {
                userRepository.fetchUserWithEmailAndPassword(email, password)
            },
            onError = {
                analytics.log(AnalyticsEvent.SignUpError(it.toString()))
                updateOrSetSuccess {
                    it.copy(
                        showNoMatchingUserError = true,
                    )
                }

            }
        ) {
            onGetUserSuccess(it?.toUiModel())
        }
    }


    private fun signUpWithEmail(email: String, password: String) {
        launchDataOperation(
            block = {
                userRepository.createUserWithEmailAndPassword(email, password)
            },
            onError = {
                snackbarManager.showSnackbar(TextWrapper.Text(it.message.toString()))
                analytics.log(AnalyticsEvent.SignUpError(it.toString()))
            }
        ) {
            onGetUserSuccess(it?.toUiModel())
        }
    }

    private fun handleSignInWithGoogleResult(result: Result<UserUiModel?>) {
        result
            .onSuccess {
                onGetUserSuccess(it)
            }.onFailure {
                snackbarManager.showSnackbar(TextWrapper.Text(it.message.toString()))
                analytics.log(AnalyticsEvent.SignUpError(it.toString()))
            }
    }

    private fun onGetUserSuccess(user: UserUiModel?) {
        val successMessage = when (loginMode) {
            LoginMode.SignIn -> StringRes.login_sign_in_success
            LoginMode.SignUp -> StringRes.login_sign_up_success
        }
        snackbarManager.showSnackbar(TextWrapper.Resource(successMessage))
        onLoginSuccess()
    }
}