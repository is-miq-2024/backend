package org.example.entities

import java.util.UUID

data class Comment(
    val id: UUID,
    val text: String,
    val userLogin: String,
    val rate: Int,
    val timestamp: Long
)
