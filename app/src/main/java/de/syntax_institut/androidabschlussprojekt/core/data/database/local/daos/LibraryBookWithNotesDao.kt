package de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.LibraryBookWithNote

@Dao
interface LibraryBookWithNotesDao {

    @Transaction
    @Query("SELECT * FROM librarybooks WHERE id = :bookId")
    suspend fun getBookWithNote(bookId: String): LibraryBookWithNote?
}