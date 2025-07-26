package de.syntax_institut.androidabschlussprojekt.core.data.database.remote.model


data class RemoteBookAPI(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val averageRating: Float?,
    val ratingsCount: Int?,
    val industryIdentifiers: List<IndustryIdentifier>?
)

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
    val extraLarge: String? = null
)

data class IndustryIdentifier(
    val type: String?,
    val identifier: String?
)