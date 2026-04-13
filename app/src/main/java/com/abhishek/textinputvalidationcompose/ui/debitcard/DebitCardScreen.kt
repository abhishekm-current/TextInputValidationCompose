package com.abhishek.textinputvalidationcompose.ui.debitcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abhishek.textinputvalidationcompose.validation.core.FormTextField
import com.abhishek.textinputvalidationcompose.validation.core.isAllValid
import com.current.digitsOnly

@Composable
fun DebitCardScreen(modifier: Modifier) {
    val viewModel: DebitCardViewModel = viewModel()
    val isAllValid by remember {
        derivedStateOf {
            isAllValid(viewModel.nameState, viewModel.cardNumberState, viewModel.expirationState, viewModel.cvvState)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        FormTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Cardholder Name",
            state = viewModel.nameState,
        )

        FormTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Card Number",
            state = viewModel.cardNumberState,
            maxLength = 16,
            inputTransformation = InputTransformation.digitsOnly(),
            nextFocusRequester = viewModel.expirationState.focusRequester
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FormTextField(
                modifier = Modifier.weight(1f),
                label = "Expiration",
                state = viewModel.expirationState,
                maxLength = 4,
                inputTransformation = InputTransformation.digitsOnly(),
                previousFocusRequester = viewModel.cardNumberState.focusRequester,
                nextFocusRequester = viewModel.cvvState.focusRequester
            )
            FormTextField(
                modifier = Modifier.weight(1f),
                label = "CVV",
                state = viewModel.cvvState,
                maxLength = 4,
                inputTransformation = InputTransformation.digitsOnly(),
                previousFocusRequester = viewModel.expirationState.focusRequester
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = if (isAllValid) "Valid" else "Invalid",
            color = if (isAllValid) Color.Green else Color.Red
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = viewModel::manualDebitCardError) {
            Text("Set Debit Card Manual Error")
        }
    }
}
