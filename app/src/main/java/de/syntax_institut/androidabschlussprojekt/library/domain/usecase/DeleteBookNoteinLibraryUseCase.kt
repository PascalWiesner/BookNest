package de.syntax_institut.androidabschlussprojekt.library.domain.usecase

import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.LibraryBookWithNoteRepository


class DeleteBookNoteinLibraryUseCase(
    private val repository: LibraryBookWithNoteRepository
) {
    suspend operator fun invoke(note: BookNote): Result<Unit> {
        return try {
            repository.deleteBookNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}