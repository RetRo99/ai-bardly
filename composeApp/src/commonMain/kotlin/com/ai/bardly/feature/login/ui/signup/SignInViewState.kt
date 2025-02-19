package com.ai.bardly.feature.login.ui.signup

import com.ai.bardly.feature.login.ui.components.LoginInputField

data class SignInViewState(
    val emailField: LoginInputField.Email = LoginInputField.Email(),
    val passwordField: LoginInputField.Password = LoginInputField.Password(),
    val isLoading: Boolean = false,
    val error: String? = null
)