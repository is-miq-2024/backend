package org.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Number pattern", description = "The pattern for number values")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NumberPattern<T>(
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Lower bound pattern")
    val lowerBound: T?,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Upper bound pattern")
    val upperBound: T?
)
