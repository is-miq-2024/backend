package org.example.mappers

import org.example.dto.UserResponse
import org.example.entities.User

fun User.toDto(): UserResponse =
    UserResponse(login, favoriteRoutes, createdRoutes, completedRoutes)