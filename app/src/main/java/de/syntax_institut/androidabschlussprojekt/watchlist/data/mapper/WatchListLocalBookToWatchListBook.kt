package de.syntax_institut.androidabschlussprojekt.watchlist.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook

fun WatchListLocalBook.toWatchListBook(): WatchListBook {
    return WatchListBook(
        id = this.id,
        title = this.title?.takeIf { it.isNotBlank() } ?: "Kein Titel",
        authors = this.authors?.takeIf { it.isNotBlank() } ?: "Unbekannt",
        description = this.description?.takeIf { it.isNotBlank() } ?: "Keine Beschreibung",
        bookCover = this.bookCover ?: "Leider ist aktuell kein BookCover verfügbar",
        isbn = this.isbn?.takeIf { it.isNotBlank() } ?: "Unbekannt"
    )
}