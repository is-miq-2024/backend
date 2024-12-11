package org.example.controllers

import org.example.dto.CreateCommentRequest
import org.example.dto.RouteFilter
import org.example.dto.RouteCreateRequest
import org.example.dto.RouteResponse
import org.example.dto.RouteUpdateRequest
import org.example.dto.UserResponse
import org.example.exception.AuthException
import org.example.mappers.RouteMapper
import org.example.mappers.UserMapper
import org.example.mappers.toEntity
import org.example.services.RouteService
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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@CrossOrigin
@RequestMapping("/route")
class RouteController(
    @Autowired private val routeService: RouteService,
    @Autowired private val routeMapper: RouteMapper,

    @Autowired private val userService: UserService,
    @Autowired private val userMapper: UserMapper
) {

    @PostMapping("/save")
    fun saveRoute(@RequestBody routeRequest: RouteCreateRequest) : RouteResponse {
        val route = routeService.save(getCurrentUserLogin(), routeRequest.toEntity())
        return routeMapper.RouteToResponseDto(
            route,
            getCurrentUserLogin(),
            routeService.getAuthor(route.id)
        )
    }

    @PostMapping("/update")
    fun updateRoute(@RequestBody routeRequest: RouteUpdateRequest) {
        routeService.update(routeRequest.toEntity())
    }

    @GetMapping("/{id}")
    fun getRoute(@PathVariable id: String) : RouteResponse {
        val route = routeService.get(UUID.fromString(id))
        return routeMapper.RouteToResponseDto(
            route,
            getCurrentUserLogin(),
            routeService.getAuthor(UUID.fromString(id))
        )
    }

    @PostMapping("/addComment")
    fun addCommentToRoute(@RequestBody createCommentRequest: CreateCommentRequest) {
        routeService.addComment(
            createCommentRequest.routeId,
            createCommentRequest.toEntity(getCurrentUserLogin())
        )
    }

    @PostMapping("/search")
    fun getRoutes(@RequestBody routeFilter: RouteFilter) : List<RouteResponse> {
        val routes = routeService.getAll(routeFilter)
        return routes.map { route ->  routeMapper.RouteToResponseDto(
            route,
            getCurrentUserLogin(),
            routeService.getAuthor(route.id)
        )}
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: String) {
        return routeService.delete(id)
    }

    @GetMapping("/findByLogin/{login}")
    fun findUserByLogin(@PathVariable login: String): ResponseEntity<UserResponse> {
        val user = userService.findByLogin(login)
        return ResponseEntity.ok(userMapper.userToDto(user))
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