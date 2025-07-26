package de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun LoginRegisterTextButton (
    onNavigateTo: () -> Unit,
    text: String
) {

    TextButton(
        onClick = {
           onNavigateTo()
        }
    ) {
        Text(text,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium)
    }

}
