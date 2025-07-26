package de.syntax_institut.androidabschlussprojekt.login.domain.usecases

import de.syntax_institut.androidabschlussprojekt.login.data.repositories.UserRepository
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService
import de.syntax_institut.androidabschlussprojekt.login.data.mappers.FirebaseAuthUserMapper

class SignInWithEmailUseCase(
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val firebaseUser = authenticationService.signInWithEmail(email, password)
            requireNotNull(firebaseUser) { "Anmeldung fehlgeschlagen – kein Benutzer zurückgegeben" }

            val user = FirebaseAuthUserMapper.toDomain(firebaseUser)
            userRepository.saveUser(user)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}