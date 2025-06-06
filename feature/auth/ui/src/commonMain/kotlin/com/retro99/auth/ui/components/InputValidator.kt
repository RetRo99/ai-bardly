package com.retro99.auth.ui.components

import com.ai.bardly.annotations.ActivityScope
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
class InputValidator {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()

    fun validateEmail(email: String): EmailValidationState {
        if (email.isBlank()) {
            return EmailValidationState.Blank
        }
        if (email.length > 50) {
            return EmailValidationState.TooLong
        }
        if (!EMAIL_REGEX.matches(email.trim())) {
            return EmailValidationState.InvalidFormat
        }
        return EmailValidationState.Success
    }

    fun validatePassword(password: String): PasswordValidationState {
        if (password.isBlank()) {
            return PasswordValidationState.Blank
        }
        if (password.length < 6) {
            return PasswordValidationState.TooShort
        }
        if (password.length > 50) {
            return PasswordValidationState.TooLong
        }
        if (!password.any { it.isDigit() }) {
            return PasswordValidationState.MissingDigit
        }
        if (!password.any { it.isLetter() }) {
            return PasswordValidationState.MissingLetter
        }
        if (!password.any { it.isUpperCase() }) {
            return PasswordValidationState.MissingCapitalLetter
        }
        return PasswordValidationState.Success
    }
}
