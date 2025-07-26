package de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.isbnmanuallyscreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

@Composable
fun ScannedBookDetails(
    book: ScannedBook,
    onSaveClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    val secureCoverUrl = book.coverUrl.replace("http://", "https://")

    AsyncImage(
        model = secureCoverUrl,
        contentDescription = "Buchcover von ${book.title}",
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    )

    Text("Titel: ${book.title}")
    Text("Autor(en): ${book.authors}")

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        onClick = onSaveClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Buch speichern")
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        onClick = onAddNoteClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Notiz hinzufügen")
    }
}