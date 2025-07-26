package de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase

import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.SaveLocalBookRepository
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

class AddBookToLibraryUseCase(
    private val repository: SaveLocalBookRepository
) {
    suspend operator fun invoke(book: ScannedBook): Result<Unit> {
        return try {
            repository.saveBookLocally(book)
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}