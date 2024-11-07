package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import java.util.UUID

@Schema(description = "Comment")
data class Comment(
    val id: UUID,
    val text: String?,
    val userLogin: String,
    val rate: Int,
    val timestamp: Instant
)
