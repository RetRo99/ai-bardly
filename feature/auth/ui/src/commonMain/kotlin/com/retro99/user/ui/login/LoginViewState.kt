package com.retro99.user.ui.login

import com.retro99.user.ui.components.LoginInputField

data class LoginViewState(
    val loginMode: LoginMode,
    val emailField: LoginInputField.Email = LoginInputField.Email(),
    val passwordField: LoginInputField.Password = LoginInputField.Password(),
    val showNoMatchingUserError: Boolean = false,
)