package com.ai.bardly.feature.login.ui.signup

data class SignInViewState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false
)