package com.abhishek.textinputvalidationcompose.validation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableStateOf

// todo create rememberValidatableTextFieldState() for easy usage
class ValidatableTextFieldState(
    private val validator: InputValidator
) {
    val textFieldState = TextFieldState()

    private val isFocused = mutableStateOf(false)
    fun setFocusState(focused: Boolean) {
        isFocused.value = focused
    }

    val validationState: ValidationState
        get() {
            val currentText = textFieldState.text.toString()
            val errorOnTextChange = validator.getErrorOnTextChange(currentText)
            val errorOnFocusLost = validator.getErrorOnFocusLost(currentText)
            return when {
                errorOnTextChange == null && errorOnFocusLost == null -> ValidationState.Valid
                else -> ValidationState.InValid(if (isFocused.value) errorOnTextChange else errorOnFocusLost)
            }
        }

    sealed interface ValidationState {
        data object Valid : ValidationState
        data class InValid(val invalidMessage: String?) : ValidationState
    }
}

fun isAllValid(vararg validatableTextFieldState: ValidatableTextFieldState) =
    validatableTextFieldState.all { it.validationState is ValidatableTextFieldState.ValidationState.Valid }