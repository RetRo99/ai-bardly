package com.ai.bardly.feature.login

object InputValidator {
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