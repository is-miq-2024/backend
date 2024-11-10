package org.example.mappers

import org.example.dto.RouteCreateRequest
import org.example.dto.RouteUpdateRequest
import org.example.entities.Route
import java.util.UUID

fun RouteCreateRequest.toEntity(): Route =
     Route(
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


fun RouteUpdateRequest.toEntity(): Route =
     Route(
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
