package org.example.mappers

import org.example.dto.CreateCommentRequest
import org.example.entities.Comment
import java.time.Instant
import java.util.UUID

fun CreateCommentRequest.toEntity(): Comment =
    Comment(
        id = UUID.randomUUID(),
        text = text,
        userLogin = login,
        rate = rate,
        timestamp = Instant.now()
    )