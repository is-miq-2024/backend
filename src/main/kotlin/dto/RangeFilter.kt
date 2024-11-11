package org.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "RangeFilter", description = "Range filter for number values")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RangeFilter<T>(
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Lower bound")
    val lowerBound: T?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Upper bound")
    val upperBound: T?
)
