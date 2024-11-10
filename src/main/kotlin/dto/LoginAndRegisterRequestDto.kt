package org.example.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request to register a new user")
data class LoginAndRegisterRequestDto(

    @Schema(description = "Username of the new user", minLength = 6, maxLength = 32)
    val username: String,

    @Schema(description = "Password for the new user", minLength = 6, maxLength = 32)
    val password: String
)