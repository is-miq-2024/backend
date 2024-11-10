package org.example.repositories

import org.example.entities.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String): Optional<User>
}