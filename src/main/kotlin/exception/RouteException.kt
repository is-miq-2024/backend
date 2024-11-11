package org.example.exception

class RouteException(
    val description: String
) : RuntimeException(description) {
    companion object {
        fun routeNotFound(id: String): UserException {
            return UserException(
                description = "route with id $id not found"
            )
        }
    }
}