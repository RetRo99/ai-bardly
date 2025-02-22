package com.ai.bardly.feature.auth.ui.components

import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.StringResource
import resources.translations.validation_email_blank
import resources.translations.validation_email_invalid_format
import resources.translations.validation_email_success
import resources.translations.validation_email_too_long
import resources.translations.validation_password_blank
import resources.translations.validation_password_missing_capital_letter
import resources.translations.validation_password_missing_digit
import resources.translations.validation_password_missing_letter
import resources.translations.validation_password_success
import resources.translations.validation_password_too_long
import resources.translations.validation_password_too_short

interface ValidationState {
    val errorResource: StringResource
}

interface SuccessValidationState

sealed class EmailValidationState(override val errorResource: StringResource) : ValidationState {
    object Success : EmailValidationState(StringRes.validation_email_success),
        SuccessValidationState

    object Blank : EmailValidationState(StringRes.validation_email_blank)
    object TooLong : EmailValidationState(StringRes.validation_email_too_long)
    object InvalidFormat : EmailValidationState(StringRes.validation_email_invalid_format)
}

sealed class PasswordValidationState(override val errorResource: StringResource) :
    ValidationState {
    object Success : PasswordValidationState(StringRes.validation_password_success),
        SuccessValidationState

    object Blank : PasswordValidationState(StringRes.validation_password_blank)
    object TooShort : PasswordValidationState(StringRes.validation_password_too_short)
    object TooLong : PasswordValidationState(StringRes.validation_password_too_long)
    object MissingDigit : PasswordValidationState(StringRes.validation_password_missing_digit)
    object MissingLetter : PasswordValidationState(StringRes.validation_password_missing_letter)
    object MissingCapitalLetter :
        PasswordValidationState(StringRes.validation_password_missing_capital_letter)
}
