package de.syntax_institut.androidabschlussprojekt.library.domain.usecase

import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.LibraryBookWithNoteRepository
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import de.syntax_institut.androidabschlussprojekt.library.domain.repository.LibraryBookRepositoryInterface


class DeleteLibraryBookWithNoteUseCase(
    private val bookRepository: LibraryBookRepositoryInterface,
    private val bookWithNoteRepository: LibraryBookWithNoteRepository
) {
    suspend operator fun invoke(book: LibraryBook): Result<Unit> {
        return try {
            bookRepository.deleteBook(book)
            val (_, note) = bookWithNoteRepository.getLibraryBookWithNoteById(book.id)
            note?.let {
                bookWithNoteRepository.deleteBookNote(it)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}