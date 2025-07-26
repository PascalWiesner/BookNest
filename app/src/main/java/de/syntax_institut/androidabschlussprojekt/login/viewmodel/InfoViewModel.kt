package de.syntax_institut.androidabschlussprojekt.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.DeleteAccountUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.SignOutUseCase

class InfoViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onSignOutClick() {
        signOutUseCase()
    }

    fun deleteAccount() {
        viewModelScope.launch {
            val result = deleteAccountUseCase()
            result.onFailure {
                _errorMessage.value = "Account konnte nicht gelöscht werden. Bitte erneut anmelden oder später versuchen."
            }
            result.onSuccess {
                _errorMessage.value = "Account erfolgreich gelöscht."
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}