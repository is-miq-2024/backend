package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import java.util.UUID

@Schema(description = "Comment")
data class Comment(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Unique identifier for the comment")
    val id: UUID,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Text content of the comment")
    val text: String?,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Login of the user who made the comment")
    val userLogin: String,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Rating provided in the comment")
    val rate: Int,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Timestamp of when the comment was made")
    val timestamp: Instant
)
