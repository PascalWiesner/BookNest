package de.syntax_institut.androidabschlussprojekt.login.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.login.presentation.composables.loginRegister.LoginRegistrationHeader
import de.syntax_institut.androidabschlussprojekt.login.viewmodel.InfoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun InfoScreen(
    onNavigateToAboutUsScreen: () -> Unit,
    onNavigateToFAQScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InfoViewModel = koinViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by viewModel.errorMessage.collectAsState()

    if (errorMessage != null) {
        LaunchedEffect(errorMessage) {
            snackbarHostState.showSnackbar(
                message = errorMessage ?: "Unbekannter Fehler",
                withDismissAction = true
            )
            viewModel.clearError()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginRegistrationHeader(
                headerText = "BookNest",
                imageResId = R.drawable.applogopng
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoCard(
                        title = "FAQ",
                        onClick = { onNavigateToFAQScreen() },
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Über uns",
                        onClick = { onNavigateToAboutUsScreen() },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoCard(
                        title = "Account löschen",
                        onClick = { viewModel.deleteAccount() },
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Logout",
                        onClick = viewModel::onSignOutClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
fun InfoCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.cardbackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .combinedClickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontSize = 21.sp
                )
            }
        }
    }
}