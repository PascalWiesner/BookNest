package de.syntax_institut.androidabschlussprojekt.library.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookNoteBottomSheet(
    book: LibraryBook,
    currentNote: BookNote?,
    onDismiss: () -> Unit,
    onSaveNote: (BookNote) -> Unit,
    onDeleteNote: (BookNote) -> Unit
) {
    val noteText = remember { mutableStateOf(currentNote?.note ?: "") }

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(book.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = noteText.value,
                onValueChange = { noteText.value = it },
                label = { Text("Notiz") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = {
                    onSaveNote(BookNote(book.id, noteText.value))
                }) {
                    Text("Speichern")
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (currentNote != null) {
                    Button(onClick = {
                        onDeleteNote(currentNote)
                        noteText.value = ""
                    }) {
                        Text("Löschen")
                    }
                }
            }
        }
    }
}
