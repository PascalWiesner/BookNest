package de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.WatchListSaveRemoteBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class ObserveWatchListBookUseCase(
    private val repository: WatchListSaveRemoteBookRepository,
) {
    operator fun invoke(isbn: String): Flow<WatchListLocalBook?> {
        return repository.observeWatchlistBookByIsbn(isbn)
            .catch { e ->
                emit(null)
            }
    }
}