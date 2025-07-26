package de.syntax_institut.androidabschlussprojekt.login.domain.usecases

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.login.data.repositories.UserRepository
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService
import de.syntax_institut.androidabschlussprojekt.login.data.mappers.FirebaseAuthUserMapper


class SignInWithGoogleUseCase(
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository
) {

    companion object {
        const val TAG = "SignInWithGoogleUseCase"
    }

    suspend operator fun invoke(authToken: String): Result<Unit> {
        return try {
            Log.i(TAG, "invoke: google sign-in with authToken=$authToken")

            val firebaseGoogleUser = authenticationService.signInWithGoogle(authToken)
            requireNotNull(firebaseGoogleUser) { "Google-Anmeldung fehlgeschlagen – kein Benutzer zurückgegeben" }

            val user = FirebaseAuthUserMapper.toDomain(firebaseGoogleUser)
            userRepository.saveUser(user)

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Fehler bei der Google-Anmeldung", e)
            Result.failure(e)
        }
    }
}