package com.abhishek.textinputvalidationcompose.validation.core

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import com.abhishek.textinputvalidationcompose.validation.core.FormTextFieldState.ValidationState
import kotlinx.coroutines.flow.drop

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    state: FormTextFieldState,
    maxLength: Int? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    previousFocusRequester: FocusRequester? = null,
    nextFocusRequester: FocusRequester? = null
) {
    val invalidMessage = (state.validationState as? ValidationState.InValid)?.invalidMessage

    OutlinedTextField(
        label = { Text(label) },
        state = state.textFieldState,
        modifier = modifier
            .focusRequester(state.focusRequester)
            .onFocusChanged { focusState ->
                state.isFocused = focusState.isFocused
            },
        inputTransformation = when {
            inputTransformation != null && maxLength != null -> inputTransformation.maxLength(maxLength)
            inputTransformation != null -> inputTransformation
            maxLength != null -> InputTransformation.maxLength(maxLength)
            else -> null
        },
        outputTransformation = outputTransformation,
        isError = invalidMessage != null,
        supportingText = { Text(invalidMessage ?: "") }
    )

    if (state.customError != null) {
        LaunchedEffect(Unit) {
            snapshotFlow { state.textFieldState.text }
                .drop(1) // ignore the current value
                .collect { state.customError = null }
        }

    }

    if (maxLength != null && nextFocusRequester != null) {
        val reachedMaxLength by remember(maxLength) {
            derivedStateOf { state.textFieldState.text.length >= maxLength }
        }

        LaunchedEffect(reachedMaxLength, nextFocusRequester) {
            if (reachedMaxLength) {
                nextFocusRequester.requestFocus()
            }
        }
    }

    if (previousFocusRequester != null) {
        val reachedZeroLength by remember {
            derivedStateOf { state.textFieldState.text.isEmpty() }
        }
        LaunchedEffect(reachedZeroLength) {
            if (reachedZeroLength) {
                previousFocusRequester.requestFocus()
            }
        }
    }
}