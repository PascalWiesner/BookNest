package de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.MyLibraryLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.mapper.toMyLibraryLocalBook
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.SaveLocalBookRepository
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook

class SaveLocalBookRepositoryImpl(
    private val dao: MyLibraryLocalBookDao
) : SaveLocalBookRepository {

    override suspend fun saveBookLocally(book: ScannedBook) {
        val entity = book.toMyLibraryLocalBook()
        dao.insertIfNotExists(entity)
    }
}