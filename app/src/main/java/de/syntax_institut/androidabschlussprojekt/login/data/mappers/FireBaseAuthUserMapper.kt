package de.syntax_institut.androidabschlussprojekt.login.data.mappers


import com.google.firebase.auth.FirebaseUser
import de.syntax_institut.androidabschlussprojekt.login.domain.models.User


object FirebaseAuthUserMapper {

    fun toDomain(dto: FirebaseUser): User {
        return User(
            id = dto.uid,
            email = dto.email ?: ""
        )
    }
}