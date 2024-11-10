package org.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import java.util.UUID

@Schema(description = "User")
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserResponse(
    @Id
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "User login")
    val login: String,

    @Schema(description = "List of favorite route IDs")
    val favoriteRoutes: List<UUID> = emptyList(),

    @Schema(description = "List of created route IDs")
    val createdRoutes: List<UUID> = emptyList(),

    @Schema(description = "List of completed route IDs")
    val completedRoutes: List<UUID> = emptyList(),
)