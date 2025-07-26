package de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase


import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.WatchListSaveRemoteBookRepository
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook

class AddRemoteBookToWatchlistUseCase(
    private val repository: WatchListSaveRemoteBookRepository
) {
    suspend operator fun invoke(book: RemoteBook): Result<Unit> {
        return try {
            repository.saveRemoteBook(book)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}