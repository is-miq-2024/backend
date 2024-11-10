package org.example.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Route filter", description = "The route filter")
data class RouteFilter(

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Page size")
    val pageSize: Int,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Page number")
    val pageNumber: Int
)
