package de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.MyLibraryLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.BookNoteRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.LibraryBookWithNoteRepository
import de.syntax_institut.androidabschlussprojekt.library.data.mapper.toLibraryBook
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook

class LibraryBookWithNoteRepositoryImpl(
    private val libraryDao: MyLibraryLocalBookDao,
    private val bookNoteRepository: BookNoteRepositoryInterface
) : LibraryBookWithNoteRepository {

    override suspend fun getLibraryBookWithNoteById(id: String): Pair<LibraryBook?, BookNote?> {
        val bookEntity = libraryDao.getBookById(id)?.toLibraryBook()
        val note = bookNoteRepository.getNoteById(id)
        return Pair(bookEntity, note)
    }

    override suspend fun saveBookNote(note: BookNote) {
        bookNoteRepository.insertBookNote(note)
    }

    override suspend fun deleteBookNote(note: BookNote) {
        bookNoteRepository.deleteBook(note)
    }
}