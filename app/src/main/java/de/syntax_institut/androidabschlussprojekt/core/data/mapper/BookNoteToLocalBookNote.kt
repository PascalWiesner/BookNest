package de.syntax_institut.androidabschlussprojekt.core.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.LocalBookNote
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote

fun BookNote.toLocalBookNote(): LocalBookNote {
    return LocalBookNote(
        id = this.id,
        note = this.note
    )
}