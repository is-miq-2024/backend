package org.example.controllers

import org.example.dto.UserResponse
import org.example.exception.AuthException
import org.example.mappers.UserMapper
import org.example.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired private val userService: UserService,
    @Autowired private val userMapper: UserMapper
) {

    @GetMapping("/findByLogin")
    fun findUserByLogin(@RequestParam login: String): ResponseEntity<UserResponse> {
        val user = userService.findByLogin(login)
        return ResponseEntity.ok(userMapper.userToDto(user))
    }

    @PostMapping("/addFavoriteRoute")
    fun addFavoriteRoute(@RequestParam routeId: UUID) {
        userService.addFavoriteRoute(getCurrentUserLogin(), routeId)
    }

    @DeleteMapping("/deleteFavoriteRoute")
    fun deleteFavoriteRoute(@RequestParam routeId: UUID) {
        userService.deleteFavoriteRoute(getCurrentUserLogin(), routeId)
    }

    @PostMapping("/addCompletedRoute")
    fun addCompletedRoute(@RequestParam routeId: UUID) {
        userService.addCompletedRoute(getCurrentUserLogin(), routeId)
    }

    @DeleteMapping("/deleteCompletedRoute")
    fun deleteCompletedRoute(@RequestParam routeId: UUID) {
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