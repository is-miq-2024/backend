package org.example.exception

class AuthException(
    val description: String
) : RuntimeException(description) {
    companion object {
        fun userAlreadyExists(username: String): AuthException {
            return AuthException(
                description = "User with username '$username' already exists"
            )
        }
    }
}
