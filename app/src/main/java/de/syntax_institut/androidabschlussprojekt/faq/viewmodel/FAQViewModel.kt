package de.syntax_institut.androidabschlussprojekt.faq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.faq.data.model.FAQ
import de.syntax_institut.androidabschlussprojekt.faq.domain.usecase.GetFAQsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FAQViewModel(
    private val getFAQsUseCase: GetFAQsUseCase
) : ViewModel() {

    private val _faqList = MutableStateFlow<List<FAQ>>(emptyList())
    val faqList: StateFlow<List<FAQ>> = _faqList

    init {
        loadFAQs()
    }

    private fun loadFAQs() {
        viewModelScope.launch {
            _faqList.value = getFAQsUseCase()
        }
    }
}