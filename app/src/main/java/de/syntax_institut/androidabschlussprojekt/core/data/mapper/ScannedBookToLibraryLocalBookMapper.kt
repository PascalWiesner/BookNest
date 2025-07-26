package de.syntax_institut.androidabschlussprojekt.core.data.mapper


import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.MyLibraryLocalBook
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

fun ScannedBook.toMyLibraryLocalBook(): MyLibraryLocalBook {
    return MyLibraryLocalBook(
        title = this.title,
        authors = this.authors,
        description = this.description,
        bookCover = this.coverUrl,
        isbn = this.isbn,
        id = this.id,
        localBookNotesid = this.localBookNotesid
    )
}