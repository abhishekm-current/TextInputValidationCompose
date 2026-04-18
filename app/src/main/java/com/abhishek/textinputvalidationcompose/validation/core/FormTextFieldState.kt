package com.abhishek.textinputvalidationcompose.validation.core

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.current.InputValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FormTextFieldState(
    coroutineScope: CoroutineScope,
    val textFieldState: TextFieldState,
    private val validators: List<InputValidator>? = null
) {
    private val _validationState = MutableStateFlow<ValidationState>(ValidationState.Valid)
    val validationState = _validationState.asStateFlow()

    init {
        if (validators != null) {
            coroutineScope.launch {
                snapshotFlow { textFieldState.text.toString() }
                    .collect { text ->
                        val validatorSequence = validators.asSequence()
                        val focusedError = validatorSequence
                            .map { it.getErrorOnTextChange(text) }
                            .firstOrNull()
                        val unfocusedError = validatorSequence
                            .map { it.getErrorOnFocusLost(text) }
                            .firstOrNull()
                        val validationState = if (focusedError != null || unfocusedError != null) {
                            ValidationState.InValid(focusedError, unfocusedError)
                        } else {
                            ValidationState.Valid
                        }

                        _validationState.value = validationState
                    }
            }
        }
    }

    fun setCustomError(error: String) {
        _validationState.value = ValidationState.InValid(error, error)
    }
}

@OptIn(SavedStateHandleSaveableApi::class)
fun ViewModel.FormTextFieldState(
    savedStateHandle: SavedStateHandle,
    validators: List<InputValidator>? = null
): FormTextFieldState {
    val textFieldState by savedStateHandle.saveable(saver = TextFieldState.Saver) { TextFieldState() }
    return FormTextFieldState(viewModelScope, textFieldState, validators)
}

fun isAllValid(vararg formTextFieldState: FormTextFieldState): Flow<Boolean> =
    combine(formTextFieldState.map { it.validationState }) { states ->
        states.all { it is ValidationState.Valid }
    }

sealed interface ValidationState {
    data object Valid : ValidationState
    data class InValid(val focusedError: String?, val unfocusedError: String?) : ValidationState
}
