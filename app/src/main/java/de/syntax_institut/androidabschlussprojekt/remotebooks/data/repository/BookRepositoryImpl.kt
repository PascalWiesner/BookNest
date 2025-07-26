
package de.syntax_institut.androidabschlussprojekt.remotebooks.data.repository

import de.syntax_institut.androidabschlussprojekt.core.data.database.remote.api.RemoteBookAPIService
import de.syntax_institut.androidabschlussprojekt.remotebooks.data.mapper.toRemoteBookModel
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.repository.RemoteBooksRepositoryInterface

class BookRepositoryImpl(
    private val apiService: RemoteBookAPIService
): RemoteBooksRepositoryInterface {

    override suspend fun getBooks(query: String, langRestrict: String?): List<RemoteBook> {
        val response = apiService.getBooks(query)
        return response.items?.map { it.toRemoteBookModel() } ?: emptyList()
    }
}