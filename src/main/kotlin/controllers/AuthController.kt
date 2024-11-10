package org.example.controllers

import org.example.dto.LoginAndRegisterRequestDto
import org.example.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

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
    fun register(@RequestBody request: LoginAndRegisterRequestDto): ResponseEntity<String> {
        authService.register(request)
        return ResponseEntity.ok("User registered successfully!")
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginAndRegisterRequestDto): ResponseEntity<String> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        return ResponseEntity.ok("Login successful!")
    }
}
