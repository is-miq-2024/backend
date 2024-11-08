package org.example.exception

class UserException(
    val description: String
) : RuntimeException(description) {
    companion object {
        fun userNotFound(login: String): UserException {
            return UserException(
                description = "user with login $login not found"
            )
        }
    }
}