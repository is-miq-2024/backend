package org.example.services

import org.example.entities.User
import org.example.exception.UserException
import org.example.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    @Autowired private val userRepository: UserRepository
) {
    fun findByLogin(login: String) : User =
        userRepository.findById(login).orElseThrow { UserException.userNotFound(login) }

    fun addFavoriteRoute(login: String, routeId: UUID) {
        val user = findByLogin(login)
        val updatedUser = user.copy(favoriteRoutes = user.favoriteRoutes + routeId)
        userRepository.save(updatedUser)
    }

    fun addCreatedRoute(login: String, routeId: UUID) {
        val user = findByLogin(login)
        val updatedUser = user.copy(createdRoutes = user.createdRoutes + routeId)
        userRepository.save(updatedUser)
    }

    fun addCompletedRoute(login: String, routeId: UUID) {
        val user = findByLogin(login)
        val updatedUser = user.copy(completedRoutes = user.completedRoutes + routeId)
        userRepository.save(updatedUser)
    }
}