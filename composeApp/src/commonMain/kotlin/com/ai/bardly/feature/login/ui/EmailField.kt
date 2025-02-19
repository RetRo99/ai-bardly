package com.ai.bardly.feature.login.ui

data class EmailField(
    val value: String = "",
    val isValid: Boolean = false
) {
    companion object {
        fun validate(email: String, validator: InputValidator): EmailField {
            return EmailField(
                value = email,
                isValid = validator.isValidEmail(email)
            )
        }
    }
}

data class PasswordField(
    val value: String = "",
    val isValid: Boolean = false,
    val isVisible: Boolean = false
) {
    companion object {
        fun validate(password: String, validator: InputValidator): PasswordField {
            return PasswordField(
                value = password,
                isValid = validator.isValidPassword(password)
            )
        }
    }
}
