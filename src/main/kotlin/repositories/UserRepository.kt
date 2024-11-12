package org.example.repositories

import org.example.entities.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun existsByLogin(login: String): Boolean

    fun findByLogin(login: String): Optional<User>
}