package de.syntax_institut.androidabschlussprojekt.library.data.mapper

import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.MyLibraryLocalBook

fun LibraryBook.toMyLibraryLocalBook(): MyLibraryLocalBook {
    return MyLibraryLocalBook(
        id = this.id,
        title = this.title,
        authors = this.authors,
        description = this.description,
        bookCover = this.bookCover,
        isbn = this.isbn,
        localBookNotesid = this.localBookNotesid
    )
}