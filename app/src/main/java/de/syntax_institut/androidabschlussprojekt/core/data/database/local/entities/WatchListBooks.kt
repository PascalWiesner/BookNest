package de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "watchlistbooks")
data class WatchListLocalBook(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String?,
    val authors: String?,
    val description: String?,
    val bookCover: String?,
    val isbn: String?
)