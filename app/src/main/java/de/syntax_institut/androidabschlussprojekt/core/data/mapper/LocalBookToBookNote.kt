package de.syntax_institut.androidabschlussprojekt.core.data.mapper

import de.syntax_institut.androidabschlussprojekt.core.data.database.local.entities.LocalBookNote
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote

fun LocalBookNote.toBookNote(): BookNote {
    return BookNote(
        id = this.id,
        note = this.note
    )
}