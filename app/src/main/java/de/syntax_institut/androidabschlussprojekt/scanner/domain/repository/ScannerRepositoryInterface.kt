package de.syntax_institut.androidabschlussprojekt.scanner.domain.repository

import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

interface ScannerRepositoryInterface {
    suspend fun getBookByIsbn(isbn: String): ScannedBook?
    suspend fun addBookToLibrary(book: ScannedBook)
}