package com.abhishek.textinputvalidationcompose.ui.debitcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abhishek.textinputvalidationcompose.validation.core.FormTextFieldState
import com.abhishek.textinputvalidationcompose.validation.validators.CvvValidator
import com.abhishek.textinputvalidationcompose.validation.validators.CardNumberValidator
import com.abhishek.textinputvalidationcompose.validation.validators.ExpirationValidator
import com.abhishek.textinputvalidationcompose.validation.validators.LengthValidator

class DebitCardViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val nameState =
        FormTextFieldState(savedStateHandle, validators = listOf(LengthValidator(3)))
    val cardNumberState =
        FormTextFieldState(savedStateHandle, validators = listOf(CardNumberValidator))
    val expirationState =
        FormTextFieldState(savedStateHandle, validators = listOf(ExpirationValidator))
    val cvvState =
        FormTextFieldState(savedStateHandle, validators = listOf(CvvValidator))

    fun manualDebitCardError() {
        cardNumberState.customError = "This card is not acceptable"
    }
}