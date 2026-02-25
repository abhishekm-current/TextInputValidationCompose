package com.abhishek.textinputvalidationcompose.validation

interface InputValidator {
    fun getErrorOnTextChange(input: String?): String?
    fun getErrorOnFocusLost(input: String?): String?
}
