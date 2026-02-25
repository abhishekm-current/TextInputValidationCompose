package com.abhishek.textinputvalidationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abhishek.textinputvalidationcompose.ui.theme.TextInputValidationComposeTheme
import com.abhishek.textinputvalidationcompose.validation.ValidatableTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextInputValidationComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InputScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun InputScreen(
    modifier: Modifier,
    viewModel: MainViewModel
) {
    Column(modifier = modifier) {
        ValidatableTextField(
            label = "Name",
            validatableTextFieldState = viewModel.nameState
        )

        ValidatableTextField(
            label = "City",
            validatableTextFieldState = viewModel.cityState
        )

        Button(
            enabled = viewModel.isScreenValid,
            onClick = {}
        ) {
            Text("Submit")
        }
    }
}

