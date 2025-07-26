package de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces


import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook

interface LibraryBookWithNoteRepository {
    suspend fun deleteBookNote(note: BookNote)
    suspend fun saveBookNote(note: BookNote)
    suspend fun getLibraryBookWithNoteById(id: String): Pair<LibraryBook?, BookNote?>
}