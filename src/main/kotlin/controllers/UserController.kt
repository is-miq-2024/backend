package org.example.controllers

import org.example.dto.UserResponse
import org.example.mappers.toDto
import org.example.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping("/findByLogin")
    fun findUserByLogin(@RequestParam login: String): ResponseEntity<UserResponse> {
        val user = userService.findByLogin(login)
        return ResponseEntity.ok(user.toDto())
    }

    @PostMapping("/addFavoriteRoute")
    fun addFavoriteRoute(@RequestParam login: String, @RequestParam routeId: UUID) {
        userService.addFavoriteRoute(login, routeId)
    }

    @PostMapping("/addCompletedRoute")
    fun addCompletedRoute(@RequestParam login: String, @RequestParam routeId: UUID) {
        userService.addCompletedRoute(login, routeId)
    }
}