package de.syntax_institut.androidabschlussprojekt.scanner.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.AddBookToLibraryUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.SaveNoteUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.ScanBookByIsbnUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ISBNManuallyViewModel(
    private val scanBookByIsbnUseCase: ScanBookByIsbnUseCase,
    private val addBookToLibraryUseCase: AddBookToLibraryUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    private val _isbn = mutableStateOf("")
    val isbn: State<String> = _isbn

    private val _bookData = mutableStateOf<ScannedBook?>(null)
    val bookData: State<ScannedBook?> = _bookData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onIsbnChanged(newIsbn: String) {
        _isbn.value = newIsbn
    }

    fun searchBook() {
        val query = _isbn.value.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            val result = scanBookByIsbnUseCase(query)
            result.onSuccess { book ->
                _bookData.value = book
            }.onFailure { e ->
                _bookData.value = null
                Log.e("ISBNManuallyViewModel", "Fehler beim Abrufen des Buches", e)
                _errorMessage.value = "Ups! Das Buch wurde leider nicht gefunden"
            }
        }
    }

    fun addBookToLibrary() {
        _bookData.value?.let { scannedBook ->
            viewModelScope.launch {
                val result = addBookToLibraryUseCase(scannedBook)
                result.onFailure { error ->
                    Log.e("ISBNManuallyViewModel", "Fehler beim hinzufügen des Buches", error)
                    _errorMessage.value = "Ups das hinzufügen des Buches hat leider nicht geklappt"
                }
            }
        }
    }

    fun saveNote(bookId: String, noteText: String) {
        viewModelScope.launch {
            val result = saveNoteUseCase(BookNote(id = bookId, note = noteText))
            result.onFailure { error ->
                Log.e("ISBNManuallyViewModel", "Fehler beim speichern der Notiz", error)
                _errorMessage.value = "Ups das speichern das Notiz hat leider nicht geklappt"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

}