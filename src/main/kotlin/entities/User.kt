package org.example.entities

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
    val password: String
)