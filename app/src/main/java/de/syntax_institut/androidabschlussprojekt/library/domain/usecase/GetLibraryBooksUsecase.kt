package de.syntax_institut.androidabschlussprojekt.library.domain.usecase

import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import de.syntax_institut.androidabschlussprojekt.library.domain.repository.LibraryBookRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetLibraryBooksUseCase(
    private val repository: LibraryBookRepositoryInterface
) {
    operator fun invoke(onError: (Throwable) -> Unit): Flow<List<LibraryBook>> {
        return repository.getAllBooks()
            .catch { e ->
                onError(e)
                emit(emptyList())
            }
    }
}