package de.syntax_institut.androidabschlussprojekt.faq.domain.repository

import de.syntax_institut.androidabschlussprojekt.faq.data.model.FAQ


interface FAQRepository {
    suspend fun getFAQs(): List<FAQ>
}