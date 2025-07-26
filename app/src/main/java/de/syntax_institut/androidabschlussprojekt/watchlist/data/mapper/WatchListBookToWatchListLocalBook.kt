package de.syntax_institut.androidabschlussprojekt.watchlist.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook

fun WatchListBook.ToWatchListLocalBook(): WatchListLocalBook {
    return WatchListLocalBook(
        id = this.id,
        title = this.title,
        authors = this.authors,
        description = this.description,
        bookCover = this.bookCover,
        isbn = this.isbn,
    )
}
