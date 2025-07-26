package de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.WatchlistLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.core.data.mapper.toWatchListBook
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.WatchListSaveRemoteBookRepository
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.watchlist.data.mapper.ToWatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import kotlinx.coroutines.flow.Flow

class WatchListSaveRemoteBookRepositoryImpl(
    private val dao: WatchlistLocalBookDao
) : WatchListSaveRemoteBookRepository {

    override suspend fun saveRemoteBook(book: RemoteBook) {
        val watchListBook = book.toWatchListBook()
        val entity = watchListBook.ToWatchListLocalBook()
        dao.insertIfNotExists(entity)
    }

    override suspend fun saveWatchListBook(book: WatchListBook) {
        val entity = book.ToWatchListLocalBook()
        dao.insertIfNotExists(entity)
    }

    override suspend fun deleteWatchlistBook(book: RemoteBook) {
        val entity = book.toWatchListBook().ToWatchListLocalBook()
        dao.delete(entity)
    }

    override fun observeWatchlistBookByIsbn(isbn: String): Flow<WatchListLocalBook?> {
        return dao.observeWatchListBook(isbn)
    }
}