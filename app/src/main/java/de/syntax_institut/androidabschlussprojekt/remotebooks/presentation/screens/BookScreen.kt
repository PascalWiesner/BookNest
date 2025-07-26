package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.bookScreen.BookGenreChips
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.core.presentation.common.composables.AppSearchBar
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel.BookViewModel
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.bookScreen.BookGrid
import org.koin.androidx.compose.koinViewModel


@Composable
fun BookScreen(
    onNavigateToToDetail: (RemoteBook) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = koinViewModel()
) {
    val books by viewModel.books.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val genres = listOf(
        "Fantasy", "Science Fiction", "Romance", "Mystery", "Thriller",
        "Horror", "Historie", "Biografie", "Poesie", "Drama"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(top = 60.dp))

        AppSearchBar(
            onSearch = { query ->
                viewModel.searchBooks(query)
            }
        )

        BookGenreChips(
            genres = genres,
            onGenreSelected = { selectedGenre ->
                viewModel.searchBooks("subject:$selectedGenre")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.applogopng),
                    contentDescription = "Fehler Bild",
                    modifier = Modifier.size(220.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = errorMessage ?: "Ein unbekannter Fehler ist aufgetreten")
            }
        } else {
            BookGrid(
                books = books,
                onBookClick = { book ->
                    onNavigateToToDetail(book)
                }
            )
        }
    }
}





