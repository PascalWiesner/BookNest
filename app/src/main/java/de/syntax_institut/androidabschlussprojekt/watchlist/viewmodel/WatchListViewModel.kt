package de.syntax_institut.androidabschlussprojekt.watchlist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.model.WatchListBook
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.usecase.DeleteWatchListBookUseCase
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.usecase.GetWatchListBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchListViewModel(
    private val getWatchListBooksUseCase: GetWatchListBooksUseCase,
    private val deleteWatchListBooksUseCase: DeleteWatchListBookUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _errorMessageDeleteBook = MutableStateFlow<String?>(null)
    val errorMessageDeleteBook: StateFlow<String?> = _errorMessageDeleteBook

    private val _errorMessageShowBooks = MutableStateFlow<String?>(null)
    val errorMessageShowBooks: StateFlow<String?> = _errorMessageShowBooks

    val books: StateFlow<List<WatchListBook>> = getWatchListBooksUseCase { e ->
        _errorMessageShowBooks.value = "Ups! Deine Merkliste konnte leider nicht geladen werden"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun deleteBook(book: WatchListBook) {
        viewModelScope.launch {
            val result =  deleteWatchListBooksUseCase(book)
            result.onFailure { error ->
                Log.e("WatchListViewModel", "Fehler beim Löschen", error)
                _errorMessageDeleteBook.value = "Ups, löschen hat leider nicht geklappt"
            }
        }
    }

    fun clearError() {
        _errorMessageDeleteBook.value = null
    }
}
