package de.syntax_institut.androidabschlussprojekt.login.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.RegisterWithEmailUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.SignInWithEmailUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val registerWithEmailUseCase: RegisterWithEmailUseCase
) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun getGoogleSignInIntent(context: Context): Intent {
        val clientId = getString(context, R.string.web_client_id)
        val signInOptions = getGoogleSignInOptions(clientId)
        val signInClient = GoogleSignIn.getClient(context, signInOptions)
        return signInClient.signInIntent
    }

    private fun getGoogleSignInOptions(serverClientId: String): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestId()
            .requestEmail()
            .requestProfile()
            .build()
    }

    fun onGoogleSignInTokenReceive(token: String) {
        viewModelScope.launch {
            val result = signInWithGoogleUseCase(token)
            result.onFailure { e ->
                Log.e("AuthenticationViewModel", "Fehler bei Google-SignIn", e)
                _errorMessage.value = "Google-Sign-In fehlgeschlagen"
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            val result = signInWithEmailUseCase(email, password)
            result.onFailure { e ->
                Log.e("AuthenticationViewModel", "Fehler bei Email-SignIn", e)
                _errorMessage.value = "Anmeldung fehlgeschlagen: E-Mail oder Passwort falsch"
            }
        }
    }

    fun registerWithEmail(email: String, password: String) {
        viewModelScope.launch {
            val result = registerWithEmailUseCase(email, password)
            result.onFailure { e ->
                Log.e("AuthenticationViewModel", "Fehler bei Registrierung", e)
                _errorMessage.value = "Registrierung fehlgeschlagen: E-Mail falsch formattiert oder Passwort nicht stark genug"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}