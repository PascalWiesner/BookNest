package de.syntax_institut.androidabschlussprojekt.scanner.data.repository

import de.syntax_institut.androidabschlussprojekt.core.data.database.remote.api.RemoteBookAPIService
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.SaveLocalBookRepository
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook
import de.syntax_institut.androidabschlussprojekt.scanner.domain.repository.ScannerRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.scanner.data.mapper.toScannedBook
import java.io.IOException


class ScannerRepositoryImpl(
    private val remoteBookApiService: RemoteBookAPIService,
    private val localBookSaver: SaveLocalBookRepository
) : ScannerRepositoryInterface {

    override suspend fun getBookByIsbn(isbn: String): ScannedBook? {
        val response = remoteBookApiService.getBooks("isbn:$isbn")
        val remoteBook = response.items?.firstOrNull()
        return remoteBook?.toScannedBook()
    }

    override suspend fun addBookToLibrary(book: ScannedBook) {
        localBookSaver.saveBookLocally(book)
    }
}