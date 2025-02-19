package com.ai.bardly.feature.auth.ui.signin

import com.ai.bardly.feature.auth.ui.components.LoginInputField

data class SignInViewState(
    val emailField: LoginInputField.Email = LoginInputField.Email(),
    val passwordField: LoginInputField.Password = LoginInputField.Password(),
    val showNoMatchingUserError: Boolean = false,
)