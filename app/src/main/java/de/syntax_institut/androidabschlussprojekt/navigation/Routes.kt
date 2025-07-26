package de.syntax_institut.androidabschlussprojekt.navigation

import kotlinx.serialization.Serializable

@Serializable
object BookRoute

@Serializable
object WatchlistRoute

@Serializable
object InfoRoute

@Serializable
object RegisterRoute

@Serializable
object LoginRoute

@Serializable
object BrowseRoute

@Serializable
object ScannerRoute

@Serializable
object ISBNNumberRoute

@Serializable
object MyLibraryRoute

@Serializable
object FAQRoute

@Serializable
object AboutUsRoute

@Serializable
data class DetailRoute(
    val id: String,
    val title: String,
    val authors: String,
    val imageUrl: String,
    val averageRating: Float,
    val ratingsCount: Int,
    val description: String,
    val isbn: String
)