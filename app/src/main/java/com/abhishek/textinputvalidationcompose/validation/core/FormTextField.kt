package com.abhishek.textinputvalidationcompose.validation.core

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    state: FormTextFieldState,
    hideErrorOnEmpty: Boolean = true,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
) {
    val validationState by state.validationState.collectAsState()
    var isFocused by remember { mutableStateOf(false) }

    val errorMessage = when (val vs = validationState) {
        ValidationState.Valid ->
            null
        is ValidationState.InValid ->
            when {
                hideErrorOnEmpty && state.textFieldState.text.isEmpty() -> null // hide error if empty
                isFocused -> vs.focusedError
                else -> vs.unfocusedError
            }
    }

    OutlinedTextField(
        label = { Text(label) },
        state = state.textFieldState,
        modifier = modifier.onFocusChanged { isFocused = it.isFocused },
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        isError = errorMessage != null,
        supportingText = errorMessage?.let { { Text(it) } }
    )
}