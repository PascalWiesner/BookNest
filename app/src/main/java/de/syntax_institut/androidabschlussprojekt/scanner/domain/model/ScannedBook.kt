package de.syntax_institut.androidabschlussprojekt.scanner.domain.model

data class ScannedBook(
    val title: String,
    val authors: String,
    val description: String,
    val coverUrl: String,
    val isbn: String,
    val id: String,
    val localBookNotesid: String
)