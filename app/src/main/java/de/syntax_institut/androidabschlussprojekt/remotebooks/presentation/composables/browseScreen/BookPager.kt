package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.browseScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun BookPager(
    books: List<RemoteBook>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        pageSpacing = 16.dp,
        modifier = modifier.fillMaxSize()
    ) { page ->
        BookCard(book = books[page])
    }
}