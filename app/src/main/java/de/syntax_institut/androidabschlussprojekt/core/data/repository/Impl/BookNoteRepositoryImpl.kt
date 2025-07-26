package de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl


import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.BookNotesLocalDao
import de.syntax_institut.androidabschlussprojekt.core.data.mapper.toBookNote
import de.syntax_institut.androidabschlussprojekt.core.data.mapper.toLocalBookNote
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.BookNoteRepositoryInterface

class BookNoteRepositoryImpl(
    private val dao: BookNotesLocalDao
) : BookNoteRepositoryInterface {

    override suspend fun getNoteById(id: String): BookNote? {
        val localNote = dao.getNoteByID(id)
        return localNote?.toBookNote()
    }

    override suspend fun insertBookNote(note: BookNote) {
        dao.insert(note.toLocalBookNote())
    }

    override suspend fun deleteBook(note: BookNote) {
        dao.delete(note.toLocalBookNote())
    }
}