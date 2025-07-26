package de.syntax_institut.androidabschlussprojekt.remotebooks.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.remote.model.RemoteBookAPI
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook

fun RemoteBookAPI.toRemoteBookModel(): RemoteBook {
    return RemoteBook(
        id = this.id,
        title = this.volumeInfo.title ?: "Kein Titel",
        authors = this.volumeInfo.authors ?: listOf("Unbekannt"),
        description = this.volumeInfo.description ?: "Keine Beschreibung vorhanden",
        thumbnail = this.volumeInfo.imageLinks?.thumbnail ?: "",
        averageRating = this.volumeInfo.averageRating ?: 0f,
        ratingsCount = this.volumeInfo.ratingsCount ?: 0,
        isbn = this.volumeInfo.industryIdentifiers?.firstOrNull()?.identifier ?: "Unbekannt"
    )
}