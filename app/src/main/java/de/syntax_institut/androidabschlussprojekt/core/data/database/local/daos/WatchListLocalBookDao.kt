package de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistLocalBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: WatchListLocalBook)

    @Query("SELECT * FROM watchlistbooks WHERE isbn = :isbn LIMIT 1")
    suspend fun getBookByTitleAndAuthor(isbn: String?): WatchListLocalBook?

    suspend fun insertIfNotExists(book: WatchListLocalBook) {
        val existing = getBookByTitleAndAuthor(book.isbn)
        if (existing == null) {
            insert(book)
        }
    }

    @Query("SELECT * from watchlistbooks ORDER BY id ASC")
    fun getAllItems(): Flow<List<WatchListLocalBook>>

    @Delete
    suspend fun delete(book: WatchListLocalBook)

    @Query("SELECT * FROM watchlistbooks WHERE isbn = :isbn LIMIT 1")
    fun observeWatchListBook(isbn: String): Flow<WatchListLocalBook?>
}