package de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

@Composable
fun ScannedBookDetails(
    book: ScannedBook,
    onSaveClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(8.dp))

    val secureCoverUrl = book.coverUrl.replace("http://", "https://")

    AsyncImage(
        model = secureCoverUrl,
        contentDescription = "Buchcover von ${book.title}",
        modifier = Modifier
            .height(100.dp)
            .width(70.dp)
            .padding(4.dp)
    )

    Text(text = "Titel: ${book.title}", color = Color.White)
    Text(text = "Autor(en): ${book.authors}", color = Color.White)

    Spacer(modifier = Modifier.height(8.dp))

    Row {
        Button(onClick = onSaveClick) {
            Text("Buch speichern")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onAddNoteClick) {
            Text("Notiz hinzufügen")
        }
    }
}