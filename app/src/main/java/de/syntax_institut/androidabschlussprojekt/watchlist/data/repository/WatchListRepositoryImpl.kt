package de.syntax_institut.androidabschlussprojekt.watchlist.data.repository

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.WatchlistLocalBookDao
import de.syntax_institut.androidabschlussprojekt.watchlist.data.mapper.ToWatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.watchlist.data.mapper.toWatchListBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.repository.WatchListBookInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WatchListRepositoryImpl(
    private val dao: WatchlistLocalBookDao
) : WatchListBookInterface {

    override fun getAllBooks(): Flow<List<WatchListBook>> {
        return dao.getAllItems().map { list ->
            list.map { it.toWatchListBook() }
        }
    }

    override suspend fun deleteBook(book: WatchListBook) {
        val entity = book.ToWatchListLocalBook()
        dao.delete(entity)
    }
}