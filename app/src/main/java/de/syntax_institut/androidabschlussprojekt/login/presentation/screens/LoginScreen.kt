package de.syntax_institut.androidabschlussprojekt.login.presentation.screens


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegisterTextButton
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegistrationCard
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegistrationHeader
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.SignInWithGoogleButton
import de.syntax_institut.androidabschlussprojekt.login.viewmodel.AuthenticationViewModel
import de.syntax_institut.androidabschlussprojekt.ui.theme.VintageWoodBrown
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val errorMessage by viewModel.errorMessage.collectAsState()

    val googleSignInIntent = remember {
        viewModel.getGoogleSignInIntent(context)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let {
                viewModel.onGoogleSignInTokenReceive(it)
            }
        } catch (e: Exception) {
            println("AuthScreen: sign-in failed: ${e.localizedMessage}")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VintageWoodBrown)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(90.dp))

        LoginRegistrationHeader(
            headerText = "BookNest",
            imageResId = R.drawable.applogopng
        )

        Spacer(Modifier.height(40.dp))


        LoginRegistrationCard(
            buttonText = "Login",
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
                viewModel.signInWithEmail(email, password)
            },
            errorMessage = errorMessage
        )

        Spacer(modifier = Modifier.height(25.dp))

        SignInWithGoogleButton(
            signInRegistration = {
                viewModel.clearError()
                launcher.launch(googleSignInIntent)
            }
        )

        LoginRegisterTextButton(
            onNavigateTo = {
                viewModel.clearError()
                onNavigateToRegister()
            },
            text = "Neu hier? Registrieren"
        )
    }
}