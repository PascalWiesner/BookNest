package de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class LibraryBookWithNote(
    @Embedded val libraryBook: MyLibraryLocalBook,

    @Relation(
        parentColumn = "localBookNotesid",
        entityColumn = "id"
    )
    val note: LocalBookNote?
)