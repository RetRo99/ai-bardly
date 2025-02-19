package com.ai.bardly.feature.login.ui.components

interface ValidationState {
    fun getLocalizedString(): String
}

interface SuccessValidationState

sealed class EmailValidationState : ValidationState {
    object Success : EmailValidationState(), SuccessValidationState
    object Blank : EmailValidationState()
    object TooLong : EmailValidationState()
    object InvalidFormat : EmailValidationState()

    override fun getLocalizedString(): String {
        return when (this) {
            Success -> "validation_email_success"
            Blank -> "validation_email_blank"
            TooLong -> "validation_email_too_long"
            InvalidFormat -> "validation_email_invalid_format"
        }
    }
}

sealed class PasswordValidationState : ValidationState {
    object Success : PasswordValidationState(), SuccessValidationState
    object Blank : PasswordValidationState()
    object TooShort : PasswordValidationState()
    object TooLong : PasswordValidationState()
    object MissingDigit : PasswordValidationState()
    object MissingLetter : PasswordValidationState()
    object MissingCapitalLetter : PasswordValidationState()

    override fun getLocalizedString(): String {
        return when (this) {
            Success -> "validation_password_success"
            Blank -> "validation_password_blank"
            TooShort -> "validation_password_too_short"
            TooLong -> "validation_password_too_long"
            MissingDigit -> "validation_password_missing_digit"
            MissingLetter -> "validation_password_missing_letter"
            MissingCapitalLetter -> "validation_password_missing_capital_letter"
        }
    }
}