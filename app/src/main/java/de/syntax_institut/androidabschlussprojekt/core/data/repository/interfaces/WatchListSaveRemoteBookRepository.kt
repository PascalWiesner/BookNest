package de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import kotlinx.coroutines.flow.Flow

interface WatchListSaveRemoteBookRepository {
    suspend fun saveRemoteBook(book: RemoteBook)
    suspend fun saveWatchListBook(book: WatchListBook)
    suspend fun deleteWatchlistBook(book: RemoteBook)
    fun observeWatchlistBookByIsbn(isbn: String): Flow<WatchListLocalBook?>

}