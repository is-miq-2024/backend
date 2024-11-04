package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "RouteType")
enum class RouteType {
    WALKING, CYCLING, DRIVING
}