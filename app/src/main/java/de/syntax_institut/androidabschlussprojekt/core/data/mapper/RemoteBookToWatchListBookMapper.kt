package de.syntax_institut.androidabschlussprojekt.core.data.mapper

import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook

fun RemoteBook.toWatchListBook(): WatchListBook {
    return WatchListBook(
        id = this.id,
        title = this.title,
        authors = this.authors.joinToString(", "),
        description = this.description,
        bookCover = this.thumbnail,
        isbn = this.isbn ?: "N/A"
    )
}