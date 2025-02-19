package com.ai.bardly.feature.login.ui.signup

import com.ai.bardly.base.ScreenIntent
import com.ai.bardly.feature.login.ui.components.LoginInputField
import dev.gitlive.firebase.auth.FirebaseUser

sealed interface SignInIntent : ScreenIntent {
    data class EmailInputChange(val email: String) : SignInIntent
    data class PasswordInputChange(val password: String) : SignInIntent
    data class TogglePasswordVisibility(val isVisible: Boolean) : SignInIntent
    data class SignInWithEmail(
        val email: LoginInputField.Email,
        val password: LoginInputField.Password
    ) : SignInIntent
    data class SignInWithGoogleResult(val result: Result<FirebaseUser?>) : SignInIntent
}