package org.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(name = "Comment Create Request", description = "Comment Create Request DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateCommentRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "The ID of the route to which the comment is added")
    val routeId: UUID,
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Text of comment")
    val text: String?,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "The login of the user who adds the comment")
    val login: String,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Route assessment")
    val rate: Int
)