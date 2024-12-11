package org.example.controllers

import org.example.dto.UserResponse
import org.example.exception.AuthException
import org.example.mappers.UserMapper
import org.example.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@CrossOrigin
@RequestMapping("/users")
class UserController(
    @Autowired private val userService: UserService,
    @Autowired private val userMapper: UserMapper
) {

    @GetMapping("/findByLogin/{login}")
    fun findUserByLogin(@PathVariable login: String): ResponseEntity<UserResponse> {
        val user = userService.findByLogin(login)
        return ResponseEntity.ok(userMapper.userToDto(user))
    }

    @PostMapping("/addFavoriteRoute/{routeId}")
    fun addFavoriteRoute(@PathVariable routeId: UUID) {
        userService.addFavoriteRoute(getCurrentUserLogin(), routeId)
    }

    @DeleteMapping("/deleteFavoriteRoute/{routeId}")
    fun deleteFavoriteRoute(@PathVariable routeId: UUID) {
        userService.deleteFavoriteRoute(getCurrentUserLogin(), routeId)
    }

    @PostMapping("/addCompletedRoute/{routeId}")
    fun addCompletedRoute(@PathVariable routeId: UUID) {
        userService.addCompletedRoute(getCurrentUserLogin(), routeId)
    }

    @DeleteMapping("/deleteCompletedRoute/{routeId}")
    fun deleteCompletedRoute(@PathVariable routeId: UUID) {
        userService.deleteCompletedRoute(getCurrentUserLogin(), routeId)
    }

    fun getCurrentUserLogin(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw AuthException.notAuthorize()
        }
        val user = authentication.principal as User
        return user.username
    }
}