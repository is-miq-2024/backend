package org.example.entities

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "users")
data class User(
    @Id
    val login: String,
    val favoriteRoutes: List<UUID>,
    val createdRoutes: List<UUID>,
    val completedRoutes: List<UUID>,
    val password: Instant
    val login: String,

    val favoriteRoutes: List<UUID> = emptyList(),

    val createdRoutes: List<UUID> = emptyList(),

    val completedRoutes: List<UUID> = emptyList(),

    val password: String
)