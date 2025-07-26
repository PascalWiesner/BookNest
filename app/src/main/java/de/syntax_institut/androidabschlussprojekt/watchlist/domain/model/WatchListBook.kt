package de.syntax_institut.androidabschlussprojekt.watchlist.domain.model

data class WatchListBook (
    val id: String,
    val title: String,
    val authors: String,
    val description: String,
    val bookCover: String,
    val isbn: String
)