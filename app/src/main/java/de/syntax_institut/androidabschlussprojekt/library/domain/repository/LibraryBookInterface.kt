package de.syntax_institut.androidabschlussprojekt.library.domain.repository

import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import kotlinx.coroutines.flow.Flow

interface LibraryBookRepositoryInterface {
    fun getAllBooks(): Flow<List<LibraryBook>>
    suspend fun deleteBook(book: LibraryBook)
}