package org.example.mappers

import org.example.dto.RouteResponse
import org.example.entities.Route
import org.example.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RouteMapper(
    @Autowired private val userService: UserService
) {
     fun RouteToResponseDto(route: Route, login: String, author: String) : RouteResponse =
        RouteResponse(
            route.id,
            route.title,
            route.description,
            route.recommendations,
            route.durationInMinutes,
            route.difficulty,
            route.types,
            route.points,
            route.comments,
            route.rate,
            userService.isFavoriteForUser(route.id, login),
            author
        )
}
