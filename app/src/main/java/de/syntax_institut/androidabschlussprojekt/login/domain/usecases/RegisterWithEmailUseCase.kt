package de.syntax_institut.androidabschlussprojekt.login.domain.usecases

import de.syntax_institut.androidabschlussprojekt.login.data.repositories.UserRepository
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService
import de.syntax_institut.androidabschlussprojekt.login.domain.models.User

class RegisterWithEmailUseCase(
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val firebaseUser = authenticationService.registerWithEmail(email, password)
            requireNotNull(firebaseUser) { "Registrierung fehlgeschlagen – kein Benutzer zurückgegeben" }

            val user = User(id = firebaseUser.uid, email = email)
            userRepository.saveUser(user)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}