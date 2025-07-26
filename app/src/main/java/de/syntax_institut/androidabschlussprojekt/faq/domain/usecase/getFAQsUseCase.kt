package de.syntax_institut.androidabschlussprojekt.faq.domain.usecase


import de.syntax_institut.androidabschlussprojekt.faq.data.model.FAQ
import de.syntax_institut.androidabschlussprojekt.faq.domain.repository.FAQRepository

class GetFAQsUseCase(
    private val repository: FAQRepository
) {
    suspend operator fun invoke(): List<FAQ> {
        return repository.getFAQs()
    }
}