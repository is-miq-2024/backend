package org.example.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Route filter", description = "The route filter")
data class RouteFilterDto(

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Page size")
    val pageSize: Int,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Page number")
    val pageNumber: Int
)
