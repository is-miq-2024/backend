package org.example.mappers

import org.example.dto.UserResponse
import org.example.entities.User
import org.example.services.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserMapper(
    @Autowired private val routeService: RouteService,
    @Autowired private val routeMapper: RouteMapper
) {
    fun userToDto(user: User): UserResponse =
        UserResponse(
            user.login,

            routeService.get(user.favoriteRoutes).map { route ->
                routeMapper.RouteToResponseDto(route, user.login, routeService.getAuthor(route.id)) },

            routeService.get(user.createdRoutes).map { route ->
                routeMapper.RouteToResponseDto(route, user.login, routeService.getAuthor(route.id)) },

            routeService.get(user.completedRoutes).map { route ->
                routeMapper.RouteToResponseDto(route, user.login, routeService.getAuthor(route.id)) }
        )
}
