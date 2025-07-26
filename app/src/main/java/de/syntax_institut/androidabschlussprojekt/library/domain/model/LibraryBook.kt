package de.syntax_institut.androidabschlussprojekt.library.domain.model

data class LibraryBook (
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val bookCover: String,
    val isbn: String,
    val localBookNotesid: String
)
