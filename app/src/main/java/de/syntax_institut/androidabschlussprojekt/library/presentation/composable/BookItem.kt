package de.syntax_institut.androidabschlussprojekt.library.presentation.composable

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook

@Composable
fun BookItem(
    book: LibraryBook,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = book.bookCover.replace("http://", "https://"),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.applogopng),
            error = painterResource(R.drawable.applogopng)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(book.authors, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(5.dp))
        Text(book.title, style = MaterialTheme.typography.titleSmall)
    }
}