package org.example.services

import org.example.dto.LoginAndRegisterRequestDto
import org.example.entities.User
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

    fun register(request: LoginAndRegisterRequestDto) {
        if (userRepository.existsByUsername(request.username)) {
            throw RuntimeException("User already exists")
        }

        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            favoriteRoutes = listOf(),
            createdRoutes = listOf(),
            completedRoutes = listOf()
        )
        userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found") }

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            listOf(SimpleGrantedAuthority("USER"))
        )
    }
}
