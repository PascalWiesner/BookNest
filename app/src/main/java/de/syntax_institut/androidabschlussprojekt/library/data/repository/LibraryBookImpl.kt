package de.syntax_institut.androidabschlussprojekt.library.data.repository

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.MyLibraryLocalBookDao
import de.syntax_institut.androidabschlussprojekt.library.data.mapper.toLibraryBook
import de.syntax_institut.androidabschlussprojekt.library.data.mapper.toMyLibraryLocalBook
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import de.syntax_institut.androidabschlussprojekt.library.domain.repository.LibraryBookRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LibraryBookRepositoryImpl(
    private val dao: MyLibraryLocalBookDao
) : LibraryBookRepositoryInterface {

    override fun getAllBooks(): Flow<List<LibraryBook>> {
        return dao.getAllItems().map { list ->
            list.map { it.toLibraryBook() }
        }
    }

    override suspend fun deleteBook(book: LibraryBook) {
        val entity = book.toMyLibraryLocalBook()
        dao.delete(entity)
    }
}