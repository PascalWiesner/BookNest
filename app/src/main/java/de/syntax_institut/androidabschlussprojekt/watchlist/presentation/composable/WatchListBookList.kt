package de.syntax_institut.androidabschlussprojekt.watchlist.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook

@Composable
fun WatchListBooksList(
    books: List<WatchListBook>,
    onItemClick: (WatchListBook) -> Unit,
    onItemLongClick: (WatchListBook) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(books.size) { index ->
            val book = books[index]
            WatchListBookItem(
                book = book,
                onClick = { onItemClick(book) },
                onLongClick = { onItemLongClick(book) }
            )
        }
    }
}