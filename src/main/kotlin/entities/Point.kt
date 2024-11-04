package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Point")
data class Point(
    val latitude: Double,
    val longitude: Double,
)
