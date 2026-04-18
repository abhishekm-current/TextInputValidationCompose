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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abhishek.textinputvalidationcompose.validation.core.FormTextField
import com.current.digitsOnly

@Composable
fun DebitCardScreen(modifier: Modifier) {
    val viewModel: DebitCardViewModel = viewModel()
    val isAllValid by viewModel.isAllValid.collectAsState(false)

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
            inputTransformation = InputTransformation.digitsOnly(),
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FormTextField(
                modifier = Modifier.weight(1f),
                label = "Expiration",
                state = viewModel.expirationState,
                inputTransformation = InputTransformation.digitsOnly(),
            )
            FormTextField(
                modifier = Modifier.weight(1f),
                label = "CVV",
                state = viewModel.cvvState,
                inputTransformation = InputTransformation.digitsOnly(),
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
