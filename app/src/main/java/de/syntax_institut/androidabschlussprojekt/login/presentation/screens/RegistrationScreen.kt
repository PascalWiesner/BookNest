package de.syntax_institut.androidabschlussprojekt.login.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegisterTextButton
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegistrationCard
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegistrationHeader
import de.syntax_institut.androidabschlussprojekt.ui.theme.VintageWoodBrown
import de.syntax_institut.androidabschlussprojekt.login.viewmodel.AuthenticationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VintageWoodBrown),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(top = 90.dp))
        LoginRegistrationHeader(
            headerText = "Registrieren",
            imageResId = R.drawable.registrationlogo
        )
        Spacer(Modifier.padding(top = 40.dp))
        LoginRegistrationCard(
            buttonText = "Registrieren",
            email = email,
            password = password,
            onEmailChanged = {
                email = it
                viewModel.clearError()
            },
            onPasswordChanged = {
                password = it
                viewModel.clearError()
            },
            onSubmit = {
                viewModel.registerWithEmail(email, password)
            },
            errorMessage = errorMessage
        )
        Spacer(Modifier.padding(top = 105.dp))
        LoginRegisterTextButton(
            onNavigateTo = {
                viewModel.clearError()
                onNavigateToLogin()
            },
            text = "Du hast schon eine Accout? Hier klicken"
        )
    }
}