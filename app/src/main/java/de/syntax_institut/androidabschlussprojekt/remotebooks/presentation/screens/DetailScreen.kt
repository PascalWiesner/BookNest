package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.detailscreen.BookImage
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.detailscreen.BookInfoCard
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.detailscreen.WatchlistButton
import de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel.DetailScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewmodel: DetailScreenViewModel = koinViewModel(),
) {
    val book = viewmodel.book
    val errorMessage by viewmodel.errorMessage.collectAsState(initial = null)
    val isInWatchList by viewmodel.isBookInWatchlist.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val imageUrl = book.thumbnail.replace("http://", "https://")

    if (errorMessage != null) {
        LaunchedEffect(errorMessage) {
            snackbarHostState.showSnackbar(
                message = errorMessage ?: "Unbekannter Fehler",
                withDismissAction = true
            )
            viewmodel.clearError()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        ) {
            BookImage(imageUrl = imageUrl)
            Spacer(modifier = Modifier.padding(10.dp))
            BookInfoCard(
                title = book.title,
                authors = book.authors,
                description = book.description,
                rating = book.averageRating,
                ratingsCount = book.ratingsCount,
                isbn = book.isbn
            )
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 42.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Zurück"
            )
        }

        WatchlistButton(
            isInWatchList = isInWatchList,
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                if (!isInWatchList) viewmodel.addToWatchlist()
                else viewmodel.removeFromWatchlist()
            }
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}