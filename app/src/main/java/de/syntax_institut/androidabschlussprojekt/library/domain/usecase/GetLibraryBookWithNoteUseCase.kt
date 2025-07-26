package de.syntax_institut.androidabschlussprojekt.library.domain.usecase

import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.LibraryBookWithNoteRepository
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook

class GetLibraryBookWithNoteUseCase(
    private val repository: LibraryBookWithNoteRepository
) {
    suspend operator fun invoke(id: String): Result<Pair<LibraryBook?, BookNote?>> {
        return try {
            val result = repository.getLibraryBookWithNoteById(id)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}