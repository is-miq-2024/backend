package org.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.example.entities.Point
import org.example.entities.RouteType

@Schema(name = "Route Update Request", description = "Route Update Request DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RouteUpdateRequest(

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Id of route")
    val id: String,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Title of route request")
    val title: String,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Description of route request")
    val description: String? = null,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Recommendations of route request")
    val recommendations: List<String>? = null,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Duration of route request")
    val durationInMinutes: Long,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Difficulty of route request")
    val difficulty: Int,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Types of route request")
    val types: List<RouteType>,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Points of route request")
    val points: List<Point>
)
