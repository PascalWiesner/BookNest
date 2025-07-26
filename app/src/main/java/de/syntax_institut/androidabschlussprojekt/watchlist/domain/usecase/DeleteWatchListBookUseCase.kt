package de.syntax_institut.androidabschlussprojekt.watchlist.domain.usecase

import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.repository.WatchListBookInterface

class DeleteWatchListBookUseCase(
    private val repository: WatchListBookInterface
) {
    suspend operator fun invoke(book: WatchListBook): Result<Unit> {
        return try {
            repository.deleteBook(book)
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}