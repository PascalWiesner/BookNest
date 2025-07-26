package de.syntax_institut.androidabschlussprojekt.login.data.repositories

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import de.syntax_institut.androidabschlussprojekt.login.domain.models.User

class UserRepository {

    private val db = Firebase.firestore
    private val collection = db.collection("users")

    fun saveUser(user: User) {
        collection.document(user.id!!).set(user)
    }

    fun deleteUser(userId: String) {
        collection.document(userId).delete()
    }
}