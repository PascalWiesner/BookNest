package de.syntax_institut.androidabschlussprojekt.watchlist.domain.usecase

import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.repository.WatchListBookInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetWatchListBooksUseCase(
    private val repository: WatchListBookInterface
) {
    operator fun invoke(onError: (Throwable) -> Unit): Flow<List<WatchListBook>> {
        return repository.getAllBooks()
            .catch { e ->
                onError(e)
                emit(emptyList())
            }
    }
}