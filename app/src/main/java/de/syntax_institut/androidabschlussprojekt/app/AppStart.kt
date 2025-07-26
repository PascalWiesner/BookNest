package de.syntax_institut.androidabschlussprojekt.app

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService
import de.syntax_institut.androidabschlussprojekt.splash.BookSplashScreen
import de.syntax_institut.androidabschlussprojekt.login.presentation.screens.LoginScreen
import de.syntax_institut.androidabschlussprojekt.login.presentation.screens.RegistrationScreen
import de.syntax_institut.androidabschlussprojekt.navigation.AppStartWithNavigation

@Composable
fun AppStart(
    modifier: Modifier = Modifier,
    authenticationService: AuthenticationService = AuthenticationService()
) {
    var showSplash by remember { mutableStateOf(true) }
    var showRegister by remember { mutableStateOf(false) }

    val isSignedIn by authenticationService.isSignedIn.collectAsState(initial = false)

    Crossfade(
        targetState = showSplash to isSignedIn,
        animationSpec = tween(durationMillis = 800)
    ) { (splash, signedIn) ->
        when {
            splash -> {
                BookSplashScreen(
                    onTimeout = {
                        showSplash = false
                    }
                )
            }

            !signedIn -> {
                if (showRegister) {
                    RegistrationScreen(
                        onNavigateToLogin = {
                           showRegister = false
                        }
                    )
                } else {
                    LoginScreen(
                        modifier = modifier,
                        onNavigateToRegister = {
                            showRegister = true
                        }
                    )
                }
            }

            else -> {
                AppStartWithNavigation()
            }
        }
    }
}