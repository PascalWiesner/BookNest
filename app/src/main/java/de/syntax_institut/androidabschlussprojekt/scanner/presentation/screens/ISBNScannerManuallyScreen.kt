package de.syntax_institut.androidabschlussprojekt.scanner.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.isbnmanuallyscreen.ISBNInputSection
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.isbnmanuallyscreen.NoteInputBottomSheet
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.isbnmanuallyscreen.ScannedBookDetails
import de.syntax_institut.androidabschlussprojekt.scanner.viewmodel.ISBNManuallyViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ISBNSCannerManuallyScreen(
    modifier: Modifier = Modifier,
    viewModel: ISBNManuallyViewModel = koinViewModel()
) {
    val isbn by viewModel.isbn
    val book by viewModel.bookData

    var showNoteSheet by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by viewModel.errorMessage.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ISBNInputSection(
                isbn = isbn,
                onIsbnChanged = viewModel::onIsbnChanged,
                onSearchClick = { viewModel.searchBook() }
            )

            book?.let { book ->
                ScannedBookDetails(
                    book = book,
                    onSaveClick = { viewModel.addBookToLibrary() },
                    onAddNoteClick = {
                        viewModel.addBookToLibrary()
                        showNoteSheet = true
                    }
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        if (showNoteSheet) {
            NoteInputBottomSheet(
                noteText = noteText,
                onNoteChange = { noteText = it },
                onSave = {
                    book?.let {
                        viewModel.saveNote(it.id, noteText)
                    }
                    noteText = ""
                    showNoteSheet = false
                },
                onDismiss = {
                    noteText = ""
                    showNoteSheet = false
                },
                sheetState = sheetState
            )
        }
    }
}