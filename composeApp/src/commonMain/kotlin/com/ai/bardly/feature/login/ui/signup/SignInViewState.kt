package com.ai.bardly.feature.login.ui.signup

import com.ai.bardly.feature.login.ui.EmailField
import com.ai.bardly.feature.login.ui.PasswordField

data class SignInViewState(
    val emailField: EmailField = EmailField(),
    val passwordField: PasswordField = PasswordField(),
    val isLoading: Boolean = false,
    val error: String? = null
)