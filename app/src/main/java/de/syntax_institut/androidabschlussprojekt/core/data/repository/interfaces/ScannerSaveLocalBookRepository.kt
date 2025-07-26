package de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces

import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

interface SaveLocalBookRepository {

    suspend fun saveBookLocally(book: ScannedBook)
}