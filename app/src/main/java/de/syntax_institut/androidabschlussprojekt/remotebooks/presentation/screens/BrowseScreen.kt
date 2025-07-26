package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.browseScreen.BookPager
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.browseScreen.ErrorMessageView
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.browseScreen.LoadingAnimation
import de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel.BrowseViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun BrowseScreen(
    modifier: Modifier = Modifier,
    viewModel: BrowseViewModel = koinViewModel()
) {
    val books by viewModel.books.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pagerState = rememberPagerState(pageCount = { books.size })

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            errorMessage != null -> {
                errorMessage?.let {
                    ErrorMessageView(message = it)
                }
            }
            books.isEmpty() -> {
                LoadingAnimation()
            }
            else -> {
                BookPager(books = books, pagerState = pagerState)
            }
        }
    }
}

