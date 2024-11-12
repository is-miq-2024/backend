package org.example.services

import org.example.dto.LoginAndRegisterRequest
import org.example.entities.User
import org.example.exception.AuthException
import org.example.repositories.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun register(request: LoginAndRegisterRequest) {
        if (userRepository.existsByLogin(request.login)) {
            throw AuthException.userAlreadyExists(request.login)
        }

        val user = User(
            login = request.login,
            password = passwordEncoder.encode(request.password),
            favoriteRoutes = listOf(),
            createdRoutes = listOf(),
            completedRoutes = listOf()
        )
        userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByLogin(username)
            .orElseThrow { UsernameNotFoundException("User with username '$username' not found") }

        return org.springframework.security.core.userdetails.User(
            user.login,
            user.password,
            listOf(SimpleGrantedAuthority("USER"))
        )
    }

    fun findUserByLogin(login: String): User {
        return userRepository.findByLogin(login)
            .orElseThrow { UsernameNotFoundException("User with login '$login' not found") }
    }
}