package com.ai.bardly.feature.auth.ui.components

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.validation_email_blank
import ai_bardly.composeapp.generated.resources.validation_email_invalid_format
import ai_bardly.composeapp.generated.resources.validation_email_success
import ai_bardly.composeapp.generated.resources.validation_email_too_long
import ai_bardly.composeapp.generated.resources.validation_password_blank
import ai_bardly.composeapp.generated.resources.validation_password_missing_capital_letter
import ai_bardly.composeapp.generated.resources.validation_password_missing_digit
import ai_bardly.composeapp.generated.resources.validation_password_missing_letter
import ai_bardly.composeapp.generated.resources.validation_password_success
import ai_bardly.composeapp.generated.resources.validation_password_too_long
import ai_bardly.composeapp.generated.resources.validation_password_too_short
import org.jetbrains.compose.resources.StringResource

interface ValidationState {
    val errorResource: StringResource
}

interface SuccessValidationState

sealed class EmailValidationState(override val errorResource: StringResource) : ValidationState {
    object Success : EmailValidationState(Res.string.validation_email_success),
        SuccessValidationState

    object Blank : EmailValidationState(Res.string.validation_email_blank)
    object TooLong : EmailValidationState(Res.string.validation_email_too_long)
    object InvalidFormat : EmailValidationState(Res.string.validation_email_invalid_format)
}

sealed class PasswordValidationState(override val errorResource: StringResource) :
    ValidationState {
    object Success : PasswordValidationState(Res.string.validation_password_success),
        SuccessValidationState

    object Blank : PasswordValidationState(Res.string.validation_password_blank)
    object TooShort : PasswordValidationState(Res.string.validation_password_too_short)
    object TooLong : PasswordValidationState(Res.string.validation_password_too_long)
    object MissingDigit : PasswordValidationState(Res.string.validation_password_missing_digit)
    object MissingLetter : PasswordValidationState(Res.string.validation_password_missing_letter)
    object MissingCapitalLetter :
        PasswordValidationState(Res.string.validation_password_missing_capital_letter)
}
