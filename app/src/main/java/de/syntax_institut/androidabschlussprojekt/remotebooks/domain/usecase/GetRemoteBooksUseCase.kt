package de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase

import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.repository.RemoteBooksRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook

class GetRemoteBooksUseCase(
    private val repository: RemoteBooksRepositoryInterface
) {
    suspend operator fun invoke(query: String, lang: String = "de"): Result<List<RemoteBook>> {
        return try {
            val books = repository.getBooks(query, lang)
            Result.success(books)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

