package de.syntax_institut.androidabschlussprojekt.watchlist.domain.repository

import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import kotlinx.coroutines.flow.Flow

interface WatchListBookInterface {
    fun getAllBooks(): Flow<List<WatchListBook>>
    suspend fun deleteBook(book: WatchListBook)
}