package org.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.example.entities.RouteType

@Schema(name = "Route filter", description = "The route filter")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RouteFilter(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Page size")
    val pageSize: Int,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Page number")
    val pageNumber: Int,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Title pattern")
    val title: String?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Duration pattern")
    val durationInMinutes: NumberPattern<Long>?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Difficulty pattern")
    val difficulty: NumberPattern<Int>?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Rate pattern")
    val rate: NumberPattern<Double>?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Types pattern")
    val types: Set<RouteType>?
)
