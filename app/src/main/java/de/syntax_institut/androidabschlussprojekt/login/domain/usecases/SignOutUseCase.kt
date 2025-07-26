package de.syntax_institut.androidabschlussprojekt.login.domain.usecases

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService

class SignOutUseCase(
    private val authenticationService: AuthenticationService
) {

    companion object {
        const val TAG = "SignOutUseCase"
    }

    operator fun invoke(): Result<Unit> {
        return try {
            Log.i(TAG, "invoke: logging out user")
            authenticationService.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Fehler beim Abmelden", e)
            Result.failure(e)
        }
    }
}