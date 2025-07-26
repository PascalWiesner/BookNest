package de.syntax_institut.androidabschlussprojekt.scanner.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.scanner.data.service.ScannerService
import de.syntax_institut.androidabschlussprojekt.scanner.domain.model.ScannedBook
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.AddBookToLibraryUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.SaveNoteUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.ScanBookByIsbnUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val scannerService: ScannerService,
    private val scanBookByIsbnUseCase: ScanBookByIsbnUseCase,
    private val addBookToLibraryUseCase: AddBookToLibraryUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    private val _scannedIsbn = mutableStateOf<String?>(null)
    val scannedIsbn: State<String?> = _scannedIsbn

    private val _bookData = mutableStateOf<ScannedBook?>(null)
    val bookData: State<ScannedBook?> = _bookData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onBarcodeDetected(isbn: String) {
        if (_scannedIsbn.value == isbn) return

        _scannedIsbn.value = isbn
        viewModelScope.launch {
            val result = scanBookByIsbnUseCase(isbn)
            result.onSuccess { book ->
                _bookData.value = book
            }.onFailure { e ->
                Log.e("BarcodeScanner", "Fehler beim Abrufen des Buches", e)
                _errorMessage.value = "Ups! Das Buch wurde leider nicht gefunden"
            }
        }
    }
    fun addBookToLibrary() {
        _bookData.value?.let { scannedBook ->
            viewModelScope.launch {
                val result = addBookToLibraryUseCase(scannedBook)
                result.onFailure { error ->
                    Log.e("ScannerViewModel", "Fehler beim hinzufügen des Buches", error)
                    _errorMessage.value = "Ups das hinzufügen des Buches hat leider nicht geklappt"
                }
            }
        }
    }

    fun getAnalyzer() = scannerService.getAnalyzer(::onBarcodeDetected)

    override fun onCleared() {
        super.onCleared()
        scannerService.close()
    }

    fun saveNote(bookId: String, noteText: String) {
        viewModelScope.launch {
            val result = saveNoteUseCase(BookNote(id = bookId, note = noteText))
            result.onFailure { error ->
                Log.e("ScannerViewModel", "Fehler beim speichern der Notiz", error)
                _errorMessage.value = "Ups das speichern das Notiz hat leider nicht geklappt"
            }
        }
    }
    fun clearError() {
        _errorMessage.value = null
    }
}