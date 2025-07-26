package de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LoginRegistrationHeader(
    headerText: String,
    @DrawableRes imageResId: Int
){
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = headerText,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )
        Spacer(Modifier.padding(top = 40.dp))
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Logo",
            modifier = Modifier.size(280.dp)
        )
    }
}