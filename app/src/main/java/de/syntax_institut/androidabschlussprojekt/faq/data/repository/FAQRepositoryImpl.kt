package de.syntax_institut.androidabschlussprojekt.faq.data.repository

import de.syntax_institut.androidabschlussprojekt.faq.data.model.FAQ
import de.syntax_institut.androidabschlussprojekt.faq.domain.repository.FAQRepository


class FAQRepositoryImpl : FAQRepository {
    override suspend fun getFAQs(): List<FAQ> {

        return listOf(
            FAQ("Wie kann ich ein Buch scannen?", "Du kannst ein Buch scannen, indem du auf den Button unten Rechts klickst und den Barcode vom Buch in die dafür vorgesehene Markierung hälst"),
            FAQ("Was ist wenn der Barcode vom Buch beschädigt ist?", "Dann kannst du oben rechts auf den Button klicken und die ISBN Nummer manuell eingeben"),
            FAQ("Wie füge ich eine Notiz hinzu?", "Du kannst eine Notiz hinzufügen, indem du auf den Button dafür im Scanner Screen klickst, oder auf das Buch in der Bibliothek "),
            FAQ("Wie lösche ich eine Notiz wieder?", "Du kannst eine Notiz löschen, indem du auf das Buch in der Bibliothek klickst und dann auf den Button löschen, dort kannst du die Notizen auch bearbeiten"),
            FAQ("Wie lösche ich ein Buch?", "Wenn du länger auf ein Buch klickst, kann du dieses sowohl in der Bibliothek als auch auf der Merkliste löschen"),
            FAQ("Wie sehe ich mehr Details vom Buch?", "Einfach auf ein Buch deiner Wahl klicken und du sieht die Kurzbeschreibung des Buches, im Bücher Screen siehst du auch die jeweilige ISBN, und die Bewertung zu dem Buch")
        )
    }
}