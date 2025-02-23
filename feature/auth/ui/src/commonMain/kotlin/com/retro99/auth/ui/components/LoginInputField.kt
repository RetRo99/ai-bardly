package com.retro99.auth.ui.components


sealed class LoginInputField(
    open val value: String = "",
    open val state: ValidationState,
    open val showErrorIfNeeded: Boolean = false,
) {

    val isValid: Boolean
        get() = state is SuccessValidationState

    val showError: Boolean
        get() = showErrorIfNeeded && !isValid

    data class Email(
        override val value: String = "",
        override val state: EmailValidationState = EmailValidationState.Blank,
        override val showErrorIfNeeded: Boolean = false,
    ) : LoginInputField(value, state, showErrorIfNeeded) {
        fun copy(
            value: String = this.value,
            showErrorIfNeeded: Boolean = this.showErrorIfNeeded,
            validator: InputValidator,
        ): Email {
            return Email(
                value = value,
                state = validator.validateEmail(value),
                showErrorIfNeeded = showErrorIfNeeded
            )
        }
    }

    data class Password(
        override val value: String = "",
        override val state: PasswordValidationState = PasswordValidationState.Blank,
        override val showErrorIfNeeded: Boolean = false,
        val isVisible: Boolean = false,
    ) : LoginInputField(value, state, showErrorIfNeeded) {
        fun copy(
            value: String = this.value,
            isPasswordVisible: Boolean = this.isVisible,
            showErrorIfNeeded: Boolean = this.showErrorIfNeeded,
            validator: InputValidator
        ): Password {
            return Password(
                value = value,
                state = validator.validatePassword(value),
                isVisible = isPasswordVisible,
                showErrorIfNeeded = showErrorIfNeeded
            )
        }
    }
}