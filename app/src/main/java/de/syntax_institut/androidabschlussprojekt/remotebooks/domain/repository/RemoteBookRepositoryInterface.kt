package de.syntax_institut.androidabschlussprojekt.remotebooks.domain.repository

import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook


interface RemoteBooksRepositoryInterface {
    suspend fun getBooks(query: String, langRestrict: String? = null): List<RemoteBook>
}