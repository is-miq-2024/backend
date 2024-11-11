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
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Duration range filter")
    val durationInMinutes: RangeFilter<Long>?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Difficulty range filter")
    val difficulty: RangeFilter<Int>?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Rate range filter")
    val rate: RangeFilter<Double>?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Types pattern")
    val types: Set<RouteType>?
)
