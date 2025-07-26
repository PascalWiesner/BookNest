package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.bookScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.theme.VintageWoodBrown

@Composable
fun BookGenreChips(
    genres: List<String>,
    onGenreSelected: (String) -> Unit
) {
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    val genreChipsText = Color(0xFFF5E9D5)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            AssistChip(
                onClick = {
                    selectedGenre = genre
                    onGenreSelected(genre)
                },
                label = { Text(genre) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedGenre == genre)
                        MaterialTheme.colorScheme.secondary
                    else
                        VintageWoodBrown,
                    labelColor = if (selectedGenre == genre)
                        Color.Black
                    else
                        genreChipsText
                )
            )
        }
    }
}