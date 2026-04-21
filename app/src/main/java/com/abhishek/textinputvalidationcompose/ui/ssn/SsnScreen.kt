package com.abhishek.textinputvalidationcompose.ui.ssn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.maxLength
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
fun SsnScreen(modifier: Modifier) {
    val viewModel: SsnViewModel = viewModel()
    val isAllValid by viewModel.isAllValid.collectAsState(false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormTextField(
                modifier = Modifier.weight(3f),
                label = "###",
                state = viewModel.firstThree,
                inputTransformation = InputTransformation.digitsOnly().maxLength(3),
            )
            FormTextField(
                modifier = Modifier.weight(2f),
                label = "##",
                state = viewModel.nextTwo,
                inputTransformation = InputTransformation.digitsOnly().maxLength(2),
            )
            FormTextField(
                modifier = Modifier.weight(4f),
                label = "####",
                state = viewModel.lastFour,
                inputTransformation = InputTransformation.digitsOnly().maxLength(4),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = if (isAllValid) "Valid" else "Invalid",
            color = if (isAllValid) Color.Green else Color.Red
        )
    }
}
