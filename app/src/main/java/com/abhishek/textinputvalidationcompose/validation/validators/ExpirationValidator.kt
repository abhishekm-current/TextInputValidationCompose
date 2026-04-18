package com.abhishek.textinputvalidationcompose.validation.validators

import com.current.InputValidator

object ExpirationValidator : InputValidator {
    override fun getErrorOnTextChange(input: String): String? {
        // only check completed input
        return if (input.length == 4) {
            checkValidExpiry(input)
        } else {
            null
        }
    }

    override fun getErrorOnFocusLost(input: String): String? {
        return checkValidExpiry(input)
    }

    private fun checkValidExpiry(input: String): String? {
        return if (input.length != 4) {
            "Enter valid mmyy"
        } else {
            val mm = input.substring(0, 2)
                .toInt()
            val yy = input.substring(2)
                .toInt()

            // everything starting from 05/26 passes
            val isValid = when {
                yy < 26 -> false
                yy == 26 -> mm in 5..12
                else -> mm in 1..12
            }
            if (isValid) null else "Enter valid expiry"
        }
    }
}