package com.abhishek.textinputvalidationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek.textinputvalidationcompose.theme.TextInputValidationComposeTheme
import com.abhishek.textinputvalidationcompose.ui.debitcard.DebitCardScreen
import com.abhishek.textinputvalidationcompose.ui.home.HomeScreen
import com.abhishek.textinputvalidationcompose.ui.ssn.SsnScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextInputValidationComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val paddedModifier = Modifier.padding(innerPadding)
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = Home) {
                        composable<Home> {
                            HomeScreen(
                                modifier = paddedModifier,
                                onSsnClick = { navController.navigate(Ssn) },
                                onDebitCardClick = { navController.navigate(DebitCard) }
                            )
                        }
                        composable<Ssn> {
                            SsnScreen(paddedModifier)
                        }
                        composable<DebitCard> {
                            DebitCardScreen(paddedModifier)
                        }
                    }
                }
            }
        }
    }
}
