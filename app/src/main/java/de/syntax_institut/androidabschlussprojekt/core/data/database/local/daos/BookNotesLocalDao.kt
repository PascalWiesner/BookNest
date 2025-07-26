package de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.LocalBookNote
import kotlinx.coroutines.flow.Flow

@Dao
interface BookNotesLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: LocalBookNote)

    @Query("SELECT * FROM localbooknotes WHERE id = :id LIMIT 1")
    suspend fun getNoteByID(id: String?): LocalBookNote?

    suspend fun insertIfNotExists(book: LocalBookNote) {
        val existing = getNoteByID(book.id)
        if (existing == null) {
            insert(book)
        }
    }

    @Query("SELECT * from localbooknotes ORDER BY id ASC")
    fun getAllItems(): Flow<List<LocalBookNote>>

    @Delete
    suspend fun delete(book: LocalBookNote)

}