package de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.MyLibraryLocalBook
import kotlinx.coroutines.flow.Flow

@Dao
interface MyLibraryLocalBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: MyLibraryLocalBook)

    @Query("SELECT * FROM librarybooks WHERE isbn = :isbn LIMIT 1")
    suspend fun getBookByIsbn(isbn: String?): MyLibraryLocalBook?

    @Query("SELECT * FROM librarybooks WHERE id = :id LIMIT 1")
    suspend fun getBookById(id: String): MyLibraryLocalBook?

    suspend fun insertIfNotExists(book: MyLibraryLocalBook) {
        val existing = getBookByIsbn(book.isbn)
        if (existing == null) {
            insert(book)
        }
    }

    @Query("SELECT * FROM librarybooks ORDER BY id ASC")
    fun getAllItems(): Flow<List<MyLibraryLocalBook>>

    @Delete
    suspend fun delete(book: MyLibraryLocalBook)
}