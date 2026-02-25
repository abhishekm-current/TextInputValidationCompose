package com.abhishek.textinputvalidationcompose.validation

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun ValidatableTextField(
    label: String,
    validatableTextFieldState: ValidatableTextFieldState
) {
    val invalidMessage = (validatableTextFieldState.validationState as? ValidatableTextFieldState.ValidationState.InValid)?.invalidMessage

    OutlinedTextField(
        label = { Text(label) },
        state = validatableTextFieldState.textFieldState,
        modifier = Modifier.onFocusChanged { focusState ->
            validatableTextFieldState.setFocusState(focusState.isFocused)
        },
        isError = invalidMessage != null,
        supportingText = { Text(invalidMessage ?: "") }
    )
}