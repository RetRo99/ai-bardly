package com.ai.bardly.feature.auth.ui.login

import com.ai.bardly.base.ScreenIntent
import com.ai.bardly.feature.auth.ui.components.LoginInputField
import dev.gitlive.firebase.auth.FirebaseUser

sealed interface LoginIntent : ScreenIntent {
    data class EmailInputChange(val email: String) : LoginIntent
    data class PasswordInputChange(val password: String) : LoginIntent
    data class TogglePasswordVisibility(val isVisible: Boolean) : LoginIntent
    data class LoginWithEmail(
        val email: LoginInputField.Email,
        val password: LoginInputField.Password
    ) : LoginIntent

    data class LoginWithGoogleResult(val result: Result<FirebaseUser?>) : LoginIntent
}