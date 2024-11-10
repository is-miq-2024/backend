package org.example.mappers

import org.example.dto.RouteCreateRequestDto
import org.example.dto.RouteUpdateRequestDto
import org.example.entities.Route
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RouteRequestMapper {
    fun RouteCreateRequestDto.toDto(): Route {
        return Route(
            id = UUID.randomUUID(),
            title = title,
            description = description.orEmpty(),
            recommendations = recommendations.orEmpty(),
            durationInMinutes = durationInMinutes,
            difficulty = difficulty,
            types = types,
            points = points,
            comments = emptyList(),
            rate = 0.0
        )
    }

    fun RouteUpdateRequestDto.toDto(): Route {
        return Route(
            id = UUID.fromString(id),
            title = title,
            description = description.orEmpty(),
            recommendations = recommendations.orEmpty(),
            durationInMinutes = durationInMinutes,
            difficulty = difficulty,
            types = types,
            points = points,
            comments = emptyList(),
            rate = 0.0
        )
    }
}