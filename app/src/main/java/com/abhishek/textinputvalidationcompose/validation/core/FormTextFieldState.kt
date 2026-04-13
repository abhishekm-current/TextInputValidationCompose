package com.abhishek.textinputvalidationcompose.validation.core

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.current.InputValidator

@Stable
class FormTextFieldState(
    val textFieldState: TextFieldState,
    private val validators: List<InputValidator>? = null
) {
    var isFocused by mutableStateOf(false)
    val focusRequester = FocusRequester()

    var customError by mutableStateOf<String?>(null)

    val validationState: ValidationState
        get() {
            when {
                customError != null -> {
                    return ValidationState.InValid(customError)
                }
                validators == null -> {
                    return ValidationState.Valid
                }
                else -> {
                    val text = textFieldState.text.toString()
                    val validatorSequence = validators.asSequence()
                    val errorOnTextChange = validatorSequence
                        .map { it.getErrorOnTextChange(text) }
                        .firstOrNull()
                    val errorOnFocusLost = validatorSequence
                        .map { it.getErrorOnFocusLost(text) }
                        .firstOrNull()
                    if (errorOnTextChange != null || errorOnFocusLost != null) {
                        return ValidationState.InValid(
                            if (isFocused) errorOnTextChange else errorOnFocusLost
                        )
                    }
                    return ValidationState.Valid
                }
            }
        }

    sealed interface ValidationState {
        data object Valid : ValidationState
        data class InValid(val invalidMessage: String?) : ValidationState
    }
}

@OptIn(SavedStateHandleSaveableApi::class)
fun FormTextFieldState(
    savedStateHandle: SavedStateHandle,
    validators: List<InputValidator>? = null
): FormTextFieldState {
    val textFieldState by savedStateHandle.saveable(saver = TextFieldState.Saver) { TextFieldState() }
    return FormTextFieldState(textFieldState, validators)
}

fun isAllValid(vararg formTextFieldState: FormTextFieldState) =
    formTextFieldState.all { it.validationState is FormTextFieldState.ValidationState.Valid }