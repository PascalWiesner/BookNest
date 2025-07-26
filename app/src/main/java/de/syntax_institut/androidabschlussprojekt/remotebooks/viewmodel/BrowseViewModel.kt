package de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.GetRemoteBooksUseCase

class BrowseViewModel(
    private val getBooksUseCase: GetRemoteBooksUseCase
) : ViewModel() {

    private val _books = MutableStateFlow<List<RemoteBook>>(emptyList())
    val books = _books.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadDefaultBooks()
    }

    private fun loadDefaultBooks() {
        viewModelScope.launch {
            val result = getBooksUseCase("bestseller")
            result.onSuccess { list ->
                _books.value = list
                _errorMessage.value = null
            }
            result.onFailure { error ->
                Log.e("BrowseViewModel", "Fehler beim abrufen der Bücher", error)
                _errorMessage.value = "Ups! Die Bücher konnten leider nicht geladen werden"
                _books.value = emptyList()
            }
        }
    }
    fun clearError() {
        _errorMessage.value = null
    }
}