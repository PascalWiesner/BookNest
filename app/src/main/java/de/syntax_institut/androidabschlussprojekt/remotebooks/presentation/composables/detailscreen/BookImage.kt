package de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.composables.detailscreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun BookImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Book Cover",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .height(220.dp)
            .width(350.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    )
}