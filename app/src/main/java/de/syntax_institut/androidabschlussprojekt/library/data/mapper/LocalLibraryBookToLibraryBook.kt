package de.syntax_institut.androidabschlussprojekt.library.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.MyLibraryLocalBook
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook

fun MyLibraryLocalBook.toLibraryBook(): LibraryBook {
    return LibraryBook(
        id = this.id,
        title = this.title?.takeIf { it.isNotBlank() } ?: "Kein Titel",
        authors = this.authors?.takeIf { it.isNotBlank() } ?: "Unbekannt",
        description = this.description?.takeIf { it.isNotBlank() } ?: "Keine Beschreibung",
        bookCover = this.bookCover ?: "",
        isbn = this.isbn?.takeIf { it.isNotBlank() } ?: "Unbekannt",
        localBookNotesid = this.localBookNotesid
    )
}