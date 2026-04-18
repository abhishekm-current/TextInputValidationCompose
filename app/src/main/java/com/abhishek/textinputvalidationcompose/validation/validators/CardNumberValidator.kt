package com.abhishek.textinputvalidationcompose.validation.validators

import com.current.ExternalDebitCard
import com.current.InputValidator

object CardNumberValidator : InputValidator {
    override fun getErrorOnTextChange(input: String): String? {
        // only check completed input
        return if (input.length == 16 && !ExternalDebitCard.validateCardNumber(input)) {
            "Invalid Card Number"
        } else {
            null
        }
    }

    override fun getErrorOnFocusLost(input: String): String? {
        return if (input.length < 16) {
            "Enter 16 digits"
        } else if (!ExternalDebitCard.validateCardNumber(input)) {
            "Invalid card number"
        } else {
            null
        }
    }
}