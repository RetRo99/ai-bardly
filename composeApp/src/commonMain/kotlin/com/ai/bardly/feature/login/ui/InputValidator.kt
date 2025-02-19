package com.ai.bardly.feature.login.ui

import com.ai.bardly.annotations.ActivityScope
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
class InputValidator {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() &&
                email.length <= 50 &&
                EMAIL_REGEX.matches(email.trim())
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() &&
                password.length >= 6 && // Minimum length
                password.length <= 50 && // Maximum length
                password.any { it.isDigit() } && // At least one number
                password.any { it.isLetter() } // At least one letter
    }
}