package com.retro99.auth.ui.components

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
    data object Success : EmailValidationState(StringRes.validation_email_success),
        SuccessValidationState

    data object Blank : EmailValidationState(StringRes.validation_email_blank)
    data object TooLong : EmailValidationState(StringRes.validation_email_too_long)
    data object InvalidFormat : EmailValidationState(StringRes.validation_email_invalid_format)
}

sealed class PasswordValidationState(override val errorResource: StringResource) :
    ValidationState {
    data object Success : PasswordValidationState(StringRes.validation_password_success),
        SuccessValidationState

    data object Blank : PasswordValidationState(StringRes.validation_password_blank)
    data object TooShort : PasswordValidationState(StringRes.validation_password_too_short)
    data object TooLong : PasswordValidationState(StringRes.validation_password_too_long)
    data object MissingDigit : PasswordValidationState(StringRes.validation_password_missing_digit)
    data object MissingLetter :
        PasswordValidationState(StringRes.validation_password_missing_letter)

    data object MissingCapitalLetter :
        PasswordValidationState(StringRes.validation_password_missing_capital_letter)
}
