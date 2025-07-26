package de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localbooknotes")
data class LocalBookNote(
    @PrimaryKey val id: String,
    val note: String
)

