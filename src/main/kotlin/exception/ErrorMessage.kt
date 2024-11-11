package org.example.exception

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Error Message")
data class ErrorMessage (
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Response status")
    var status: Int,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Response message")
    var message: String
)
