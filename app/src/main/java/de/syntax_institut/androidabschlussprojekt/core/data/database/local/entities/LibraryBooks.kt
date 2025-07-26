package de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "librarybooks")
data class MyLibraryLocalBook(
    @PrimaryKey val id: String,
    val localBookNotesid: String,
    val title: String?,
    val authors: String?,
    val description: String?,
    val bookCover: String?,
    val isbn: String?
)

