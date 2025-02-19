package com.ai.bardly.feature.login.ui.signin

import com.ai.bardly.feature.login.ui.components.LoginInputField

data class SignInViewState(
    val emailField: LoginInputField.Email = LoginInputField.Email(),
    val passwordField: LoginInputField.Password = LoginInputField.Password(),
)