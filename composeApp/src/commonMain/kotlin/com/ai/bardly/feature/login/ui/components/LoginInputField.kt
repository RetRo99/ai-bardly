package com.ai.bardly.feature.login.ui.components


sealed class LoginInputField(
    open val value: String = "",
    open val state: ValidationState
) {

    val isValid: Boolean
        get() = state is SuccessValidationState

    data class Email(
        override val value: String = "",
        override val state: EmailValidationState = EmailValidationState.Blank,
    ) : LoginInputField(value, state) {
        fun copy(value: String = this.value, validator: InputValidator): Email {
            return Email(value = value, state = validator.validateEmail(value))
        }
    }

    data class Password(
        override val value: String = "",
        override val state: PasswordValidationState = PasswordValidationState.Blank,
        val isVisible: Boolean = false,
    ) : LoginInputField(value, state) {
        fun copy(
            value: String = this.value,
            isPasswordVisible: Boolean = this.isVisible,
            validator: InputValidator
        ): Password {
            return Password(
                value = value,
                state = validator.validatePassword(value),
                isVisible = isPasswordVisible
            )
        }
    }
}