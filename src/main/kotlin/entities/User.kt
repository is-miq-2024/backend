package org.example.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "users")
data class User(
    @Id
    val username: String,

    val favoriteRoutes: List<UUID> = emptyList(),

    val createdRoutes: List<UUID> = emptyList(),

    val completedRoutes: List<UUID> = emptyList(),

    val password: String
)