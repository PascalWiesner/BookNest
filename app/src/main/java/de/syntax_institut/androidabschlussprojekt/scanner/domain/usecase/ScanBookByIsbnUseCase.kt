package de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase

import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook
import de.syntax_institut.androidabschlussprojekt.scanner.domain.repository.ScannerRepositoryInterface

class ScanBookByIsbnUseCase(
    private val repository: ScannerRepositoryInterface
) {
    suspend operator fun invoke(isbn: String): Result<ScannedBook?> {
        return try {
            val book = repository.getBookByIsbn(isbn)
            Result.success(book)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}