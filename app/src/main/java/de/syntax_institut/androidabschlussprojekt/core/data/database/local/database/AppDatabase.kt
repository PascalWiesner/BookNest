package de.syntax_institut.androidabschlussprojekt.core.data.database.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.BookNotesLocalDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.MyLibraryLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.WatchlistLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.LocalBookNote
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.MyLibraryLocalBook
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.WatchListLocalBook

@Database(entities =  [MyLibraryLocalBook::class, WatchListLocalBook::class, LocalBookNote::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun myLibrarylocalBookDao(): MyLibraryLocalBookDao
    abstract fun watchlistLocalBookDao(): WatchlistLocalBookDao
    abstract fun localBookNoteDao(): BookNotesLocalDao

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, AppDatabase::class.java, "localbook_database")
                    .build().also { Instance = it }
            }
        }
    }
}
