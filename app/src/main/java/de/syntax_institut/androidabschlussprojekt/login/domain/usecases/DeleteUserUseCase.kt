package de.syntax_institut.androidabschlussprojekt.login.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import de.syntax_institut.androidabschlussprojekt.login.data.repositories.UserRepository
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService

class DeleteAccountUseCase(
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
                ?: throw Exception("Kein eingeloggter Benutzer")

            userRepository.deleteUser(userId)

            val success = authenticationService.deleteAccount()
            if (!success) throw Exception("Konto konnte nicht gelöscht werden")

            authenticationService.signOut()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}