package com.retro99.auth.ui.login

import UserUiModel
import com.retro99.auth.ui.components.LoginInputField
import com.retro99.base.ui.BaseScreenIntent

sealed interface LoginIntent : BaseScreenIntent {

    data class EmailInputChange(val email: String) : LoginIntent
    data class PasswordInputChange(val password: String) : LoginIntent
    data class TogglePasswordVisibility(val isVisible: Boolean) : LoginIntent
    data class LoginWithEmail(
        val email: LoginInputField.Email,
        val password: LoginInputField.Password
    ) : LoginIntent

    data class LoginWithGoogleResult(val result: Result<UserUiModel?>) : LoginIntent
    data object OnFooterClicked : LoginIntent
}