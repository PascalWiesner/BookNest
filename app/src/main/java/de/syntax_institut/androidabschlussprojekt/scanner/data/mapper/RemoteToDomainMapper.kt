package de.syntax_institut.androidabschlussprojekt.scanner.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.remote.model.RemoteBookAPI
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook


fun RemoteBookAPI.toScannedBook(): ScannedBook {
    val volume = this.volumeInfo

    return ScannedBook(
        title = volume.title?.takeIf { it.isNotBlank() } ?: "Kein Titel",
        authors = volume.authors?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "Unbekannt",
        description = volume.description?.takeIf { it.isNotBlank() } ?: "Keine Beschreibung",
        coverUrl = volume.imageLinks?.thumbnail ?: "",
        isbn = volume.industryIdentifiers?.firstOrNull()?.identifier ?: "Unbekannt",
        id = this.id,
        localBookNotesid = null.toString()
    )
}