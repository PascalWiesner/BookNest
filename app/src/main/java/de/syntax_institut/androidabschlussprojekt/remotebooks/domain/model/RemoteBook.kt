package de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model

data class RemoteBook(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnail: String,
    val description: String,
    val averageRating: Float,
    val ratingsCount: Int,
    val isbn: String?
)