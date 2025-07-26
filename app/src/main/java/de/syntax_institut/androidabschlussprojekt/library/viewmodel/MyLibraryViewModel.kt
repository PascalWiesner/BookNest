package de.syntax_institut.androidabschlussprojekt.library.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.core.data.model.BookNote
import de.syntax_institut.androidabschlussprojekt.library.domain.model.LibraryBook
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.DeleteBookNoteinLibraryUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.DeleteLibraryBookWithNoteUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.GetLibraryBookWithNoteUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.GetLibraryBooksUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.SaveLibraryBookNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class LibraryScreenViewModel(
    private val getLibraryBooksUseCase: GetLibraryBooksUseCase,
    private val deleteLibraryBookWithNoteUseCase: DeleteLibraryBookWithNoteUseCase,
    private val getLibraryBookWithNoteUseCase: GetLibraryBookWithNoteUseCase,
    private val deleteBookNoteinLibraryUseCase: DeleteBookNoteinLibraryUseCase,
    private val saveLibraryBookNoteUseCase: SaveLibraryBookNoteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _errorMessageShowBooks = MutableStateFlow<String?>(null)
    val errorMessageShowBooks: StateFlow<String?> = _errorMessageShowBooks

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    val books: StateFlow<List<LibraryBook>> = getLibraryBooksUseCase {
        _errorMessageShowBooks.value = "Ups! Die Bücher konnten leider nicht geladen werden"
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredBooks = combine(_searchQuery, books) { query, bookList ->
        if (query.isBlank()) {
            bookList
        } else {
            bookList.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.authors.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentBookNote = MutableStateFlow<BookNote?>(null)
    val currentBookNote: StateFlow<BookNote?> = _currentBookNote

    fun loadBookWithNoteById(id: String) {
        viewModelScope.launch {
            val result = getLibraryBookWithNoteUseCase(id)
            result.onSuccess { (_, note) ->
                _currentBookNote.value = note
                _errorMessage.value = null
            }
            result.onFailure { error ->
                Log.e("LibraryScreenViewModel", "Fehler beim Laden der Buchnotiz", error)
                _errorMessage.value = "Fehler beim Laden der Buchnotiz"
            }
        }
    }

    fun saveNote(note: BookNote) {
        viewModelScope.launch {
            val result = saveLibraryBookNoteUseCase(note)
            result.onSuccess {
                _currentBookNote.value = note
                _errorMessage.value = null
            }
            result.onFailure { error ->
                Log.e("LibraryScreenViewModel", "Fehler beim Speichern der Buchnotiz", error)
                _errorMessage.value = "Fehler beim Speichern der Buchnotiz"
            }
        }
    }

    fun deleteNote(note: BookNote) {
        viewModelScope.launch {
            val result = deleteBookNoteinLibraryUseCase(note)
            result.onSuccess {
                _currentBookNote.value = null
                _errorMessage.value = null
            }
            result.onFailure { error ->
                Log.e("LibraryScreenViewModel", "Fehler beim Löschen der Buchnotiz", error)
                _errorMessage.value = "Fehler beim Löschen der Buchnotiz"
            }
        }
    }

    fun deleteBook(book: LibraryBook) {
        viewModelScope.launch {
            val result = deleteLibraryBookWithNoteUseCase(book)
            result.onSuccess {
                if (_currentBookNote.value?.id == book.id) {
                    _currentBookNote.value = null
                }
                _errorMessage.value = null
            }
            result.onFailure { error ->
                Log.e("LibraryScreenViewModel", "Fehler beim Löschen des Buches", error)
                _errorMessage.value = "Fehler beim Löschen des Buches"
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun clearError() {
        _errorMessage.value = null
    }
}