package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.bookScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook


@Composable
fun BookGrid(
    modifier: Modifier = Modifier,
    onBookClick: (RemoteBook) -> Unit,
    books: List<RemoteBook>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(8.dp)
    ) {
        items(books) { book ->
            BookItem(
                book = book,
                onClick = { onBookClick(book) }
            )
        }
    }
}