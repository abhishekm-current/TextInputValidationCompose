package com.abhishek.textinputvalidationcompose.validation.validators

import com.current.InputValidator

class LengthValidator(private val requiredLength: Int) : InputValidator {
    override fun getErrorOnTextChange(input: String): String? {
        return null
    }

    override fun getErrorOnFocusLost(input: String): String? {
        return if (input.length < requiredLength) {
            "need $requiredLength chars"
        } else {
            null
        }
    }
}