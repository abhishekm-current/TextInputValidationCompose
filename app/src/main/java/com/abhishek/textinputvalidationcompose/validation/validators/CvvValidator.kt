package com.abhishek.textinputvalidationcompose.validation.validators

import com.current.InputValidator

object CvvValidator : InputValidator {
    override fun getErrorOnTextChange(input: String): String? {
        return null
    }

    override fun getErrorOnFocusLost(input: String): String? {
        // both 3 and 4 are okay
        return if (input.length < 3) {
            "Enter 3/4 chars"
        } else {
            null
        }
    }
}