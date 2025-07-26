package de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R


@Composable
fun SignInWithGoogleButton(
    signInRegistration: () -> Unit,
) {
    Image(
        painter = painterResource(R.drawable.googlelogo),
        contentDescription = "GoogleLogo",
        modifier = Modifier
            .size(80.dp)
            .clickable {
                signInRegistration()
            }
    )
}