package de.syntax_institut.androidabschlussprojekt.scanner.presentation.composables.scannerscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

@Composable
fun ScannerBottomInfo(
    scannedIsbn: String?,
    book: ScannedBook?,
    onSaveClick: () -> Unit,
    onAddNoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(bottom = 70.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bitte Barcode in den Rahmen halten",
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        scannedIsbn?.let {
            Text(
                text = "Gescannt: $it",
                color = Color.White
            )
        }

        book?.let {
            ScannedBookDetails(
                book = it,
                onSaveClick = onSaveClick,
                onAddNoteClick = onAddNoteClick
            )
        }
    }
}