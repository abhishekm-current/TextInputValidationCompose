package com.abhishek.textinputvalidationcompose.validation.validators

import androidx.core.text.trimmedLength
import com.current.InputValidator

class LengthValidator(private val requiredLength: Int) : InputValidator {
    override fun getErrorOnTextChange(input: String): String? {
        return null
    }

    override fun getErrorOnFocusLost(input: String): String? {
        return if (input.trimmedLength() < requiredLength) {
            "Enter at least $requiredLength chars"
        } else {
            null
        }
    }
}