package de.syntax_institut.androidabschlussprojekt.library.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import de.syntax_institut.androidabschlussprojekt.library.presentation.composable.BookItem
import de.syntax_institut.androidabschlussprojekt.library.presentation.composable.BookNoteBottomSheet
import de.syntax_institut.androidabschlussprojekt.library.presentation.composable.DeleteBookDialog
import de.syntax_institut.androidabschlussprojekt.library.presentation.composable.ErrorFallback
import de.syntax_institut.androidabschlussprojekt.library.viewmodel.LibraryScreenViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LibraryScreen(viewModel: LibraryScreenViewModel = koinViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessageShowBooks by viewModel.errorMessageShowBooks.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val books by viewModel.filteredBooks.collectAsState()
    val currentNote by viewModel.currentBookNote.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<LibraryBook?>(null) }

    val openDialog = remember { mutableStateOf(false) }


    if (errorMessage != null) {
        LaunchedEffect(errorMessage) {
            snackbarHostState.showSnackbar(
                message = errorMessage ?: "Unbekannter Fehler",
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
            Spacer(modifier = Modifier.height(60.dp))

            AppSearchBar(onSearch = { query -> viewModel.onSearchQueryChanged(query) })

                if (errorMessageShowBooks != null && books.isEmpty()) {
                    ErrorFallback()

            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(books) { book ->
                        BookItem(
                            book = book,
                            onClick = {
                                selectedBook = book
                                viewModel.loadBookWithNoteById(book.id)
                                showBottomSheet = true
                            },
                            onLongClick = {
                                selectedBook = book
                                openDialog.value = true
                            }
                        )
                    }
                }
            }
        }

        if (showBottomSheet && selectedBook != null) {
            BookNoteBottomSheet(
                book = selectedBook!!,
                currentNote = currentNote,
                onDismiss = {
                    showBottomSheet = false
                    selectedBook = null
                },
                onSaveNote = { note ->
                    viewModel.saveNote(note)
                },
                onDeleteNote = { note ->
                    viewModel.deleteNote(note)
                }
            )
        }

        if (openDialog.value && selectedBook != null) {
            DeleteBookDialog(
                book = selectedBook!!,
                onDismiss = {
                    openDialog.value = false
                },
                onConfirm = {
                    viewModel.deleteBook(selectedBook!!)
                    openDialog.value = false
                }
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}