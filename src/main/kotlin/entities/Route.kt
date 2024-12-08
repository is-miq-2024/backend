package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration
import java.util.UUID

@Document(collection = "routes")
data class Route(
    @Id
    val id: UUID,

    val title: String,

    val description: String,

    val recommendations: List<String>,

    val durationInMinutes: Long,

    val difficulty: Int,

    val types: List<RouteType>,

    val points: List<Point>,

    val comments: List<Comment>,

    val rate: Double
)
