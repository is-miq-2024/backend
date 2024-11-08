package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration
import java.util.UUID

@Document(collection = "routes")
data class Route(
    @Id
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Route id")
    val id: UUID,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Name of the route")
    val title: String,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Description of the route")
    val description: String,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Recommendations of the route")
    val recommendations: List<String>,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Duration in minutes of the route")
    val durationInMinutes: Long,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Difficulty of the route")
    val difficulty: Int,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Types of the route")
    val types: List<RouteType>,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Points of the route")
    val points: List<Point>,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Comments of the route")
    val comments: List<Comment>,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Route rate")
    val rate: Double
)
