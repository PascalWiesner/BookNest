package de.syntax_institut.androidabschlussprojekt.watchlist.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.core.presentation.common.composables.AppSearchBar
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import de.syntax_institut.androidabschlussprojekt.watchlist.presentation.composable.BookDetailBottomSheet
import de.syntax_institut.androidabschlussprojekt.watchlist.presentation.composable.DeleteBookDialog
import de.syntax_institut.androidabschlussprojekt.watchlist.presentation.composable.WatchListBooksList
import de.syntax_institut.androidabschlussprojekt.watchlist.presentation.composable.WatchListErrorMessage
import de.syntax_institut.androidabschlussprojekt.watchlist.viewmodel.WatchListViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = koinViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessageDeleteBook by viewModel.errorMessageDeleteBook.collectAsState()
    val errorMessageShowBooks by viewModel.errorMessageShowBooks.collectAsState()

    val filteredBooks by viewModel.filteredBooks.collectAsState()
    var openDialog by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<WatchListBook?>(null) }

    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetBook by remember { mutableStateOf<WatchListBook?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (errorMessageDeleteBook != null) {
        LaunchedEffect(errorMessageDeleteBook) {
            snackbarHostState.showSnackbar(
                message = errorMessageDeleteBook ?: "Unbekannter Fehler",
                withDismissAction = true
            )
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 60.dp))

                AppSearchBar(
                    onSearch = { query ->
                        viewModel.onSearchQueryChanged(query)
                    }
                )

                if (errorMessageShowBooks != null && filteredBooks.isEmpty()) {
                    WatchListErrorMessage()
                }  else {
                    WatchListBooksList(
                        books = filteredBooks,
                        onItemClick = {
                            sheetBook = it
                            showBottomSheet = true
                        },
                        onItemLongClick = {
                            selectedBook = it
                            openDialog = true
                        }
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

    if (showBottomSheet) {
        sheetBook?.let { book ->
            BookDetailBottomSheet(
                book = book,
                onDismiss = {
                    showBottomSheet = false
                    sheetBook = null
                },
                sheetState = sheetState
            )
        }
    }

    if (openDialog) {
        selectedBook?.let { book ->
            DeleteBookDialog(
                selectedBook = book,
                onDismiss = { openDialog = false },
                onConfirm = {
                    viewModel.deleteBook(book)
                    openDialog = false
                }
            )
        }
    }
}


