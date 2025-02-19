package com.ai.bardly.feature.auth.ui.login

import com.ai.bardly.feature.auth.ui.components.LoginInputField

data class LoginViewState(
    val emailField: LoginInputField.Email = LoginInputField.Email(),
    val passwordField: LoginInputField.Password = LoginInputField.Password(),
    val showNoMatchingUserError: Boolean = false,
)