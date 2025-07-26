package de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import de.syntax_institut.androidabschlussprojekt.navigation.DetailRoute
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.AddRemoteBookToWatchlistUseCase
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.ObserveWatchListBookUseCase
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.RemoveRemoteBookFromWatchlistUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val addRemoteBookToWatchlistUseCase: AddRemoteBookToWatchlistUseCase,
    private val observeWatchListBookUseCase: ObserveWatchListBookUseCase,
    private val removeRemoteBookFromWatchlistUseCase: RemoveRemoteBookFromWatchlistUseCase
) : ViewModel() {

    private val args = savedStateHandle.toRoute<DetailRoute>()

    val book: RemoteBook = RemoteBook(
        id = args.id,
        title = args.title,
        authors = args.authors.split(",").map { it.trim() },
        description = args.description,
        thumbnail = args.imageUrl,
        isbn = args.isbn,
        averageRating = args.averageRating,
        ratingsCount = args.ratingsCount
    )

    val isBookInWatchlist = observeWatchListBookUseCase(book.isbn ?: "")
        .map { it != null }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage

    fun addToWatchlist() {
        viewModelScope.launch {
            val result = runCatching { addRemoteBookToWatchlistUseCase(book) }
            result.onFailure { error ->
                Log.e("DetailScreenViewModel", "Fehler beim löschen der Bücher", error)
                _errorMessage.value = "Ups! Das hinzufügen des Buches hat leider nicht geklappt"
            }
        }
    }

    fun removeFromWatchlist() {
        viewModelScope.launch {
            val result = runCatching { removeRemoteBookFromWatchlistUseCase(book) }
            result.onFailure { error ->
                Log.e("DetailScreenViewModel", "Fehler beim löschen der Bücher", error)
                _errorMessage.value = "Ups! Das löschen hat leider nicht geklappt"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}