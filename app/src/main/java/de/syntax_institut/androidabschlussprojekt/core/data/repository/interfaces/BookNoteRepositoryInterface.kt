package de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces

import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote

interface BookNoteRepositoryInterface {
    suspend fun insertBookNote(note: BookNote)
    suspend fun deleteBook(note: BookNote)
    suspend fun getNoteById(id: String): BookNote?
}