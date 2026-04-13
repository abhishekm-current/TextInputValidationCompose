package com.current

interface InputValidator {
    fun getErrorOnTextChange(input: String): String?
    fun getErrorOnFocusLost(input: String): String?
}