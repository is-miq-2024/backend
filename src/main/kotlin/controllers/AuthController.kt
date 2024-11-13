package org.example.controllers

import org.example.dto.LoginAndRegisterRequest
import org.example.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val authenticationConfiguration: AuthenticationConfiguration
) {

    private val authenticationManager: AuthenticationManager by lazy {
        authenticationConfiguration.authenticationManager
    }

    @PostMapping("/register")
    fun register(@RequestBody request: LoginAndRegisterRequest): ResponseEntity<Map<String, String>> {
        authService.register(request.login, request.password)
        return ResponseEntity.ok(
            mapOf(
                "message" to "User registered successfully!",
                "userLogin" to request.login
            )
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginAndRegisterRequest): ResponseEntity<Map<String, Any>> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.login, request.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user = authService.findUserByLogin(request.login)
        val response = mapOf(
            "message" to "Login successful!",
            "userId" to user.login
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<Map<String, String>> {
        val currentUser = SecurityContextHolder.getContext().authentication?.name ?: ""
        SecurityContextHolder.clearContext()
        return ResponseEntity.ok(
            mapOf(
                "message" to "Logout successful!",
                "userId" to currentUser
            )
        )
    }
}
