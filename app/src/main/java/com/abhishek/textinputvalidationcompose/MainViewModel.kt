package com.abhishek.textinputvalidationcompose

import androidx.lifecycle.ViewModel
import com.abhishek.textinputvalidationcompose.validation.InputValidator
import com.abhishek.textinputvalidationcompose.validation.ValidatableTextFieldState
import com.abhishek.textinputvalidationcompose.validation.isAllValid

class MainViewModel : ViewModel() {

    val nameState = ValidatableTextFieldState(
        validator = CustomLengthValidator(3, 8)
    )

    val cityState = ValidatableTextFieldState(
        validator = CustomLengthValidator(2, 5, isEmptyValid = true)
    )

    val isScreenValid: Boolean
        get() = isAllValid(nameState, cityState)
}

/**
 * A sample validator implementation for demonstration/test etc
 *
 * @param minLength When user is focused and typing, error is shown based on this
 * @param fullLength When user focuses elsewhere, error is shown based on this
 * @param isEmptyValid Whether empty text is considered to be valid
 */
class CustomLengthValidator(
    private val minLength: Int,
    private val fullLength: Int,
    private val isEmptyValid: Boolean = false
) : InputValidator {
    override fun getErrorOnTextChange(input: String?): String? {
        val length = input?.length ?: 0
        return when {
            length == 0 && isEmptyValid -> null
            length < minLength -> "Add minimum $minLength letters"
            else -> null
        }
    }

    override fun getErrorOnFocusLost(input: String?): String? {
        val length = input?.length ?: 0
        return when {
            length == 0 && isEmptyValid -> null
            length < fullLength -> "Add full $fullLength letters"
            else -> null
        }
    }
}