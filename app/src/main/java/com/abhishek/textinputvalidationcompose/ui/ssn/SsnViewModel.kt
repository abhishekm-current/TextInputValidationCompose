package com.abhishek.textinputvalidationcompose.ui.ssn

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abhishek.textinputvalidationcompose.validation.core.FormTextFieldState
import com.abhishek.textinputvalidationcompose.validation.validators.LengthValidator

class SsnViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val firstThree = FormTextFieldState(savedStateHandle, listOf(LengthValidator(3)))
    val nextTwo = FormTextFieldState(savedStateHandle, listOf(LengthValidator(2)))
    val lastFour = FormTextFieldState(savedStateHandle, listOf(LengthValidator(4)))

    fun manualSsnError() {
        firstThree.setCustomError("try again")
    }
}

