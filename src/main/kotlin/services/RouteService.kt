package org.example.services

import org.example.dto.RouteFilter
import org.example.entities.Comment
import org.example.entities.Route
import org.example.exception.RouteException
import org.example.repositories.RouteRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RouteService(
    private val routeRepository: RouteRepository,
    private val userService: UserService
) {

    fun save(login: String, route: Route): Route {
        val route = routeRepository.save(route)
        userService.addCreatedRoute(login, route.id)
        return route
    }

    fun update(route: Route): Route {
        routeRepository.deleteById(route.id)
        return routeRepository.save(route)
    }

    fun get(id: String): Route =
        routeRepository.findById(UUID.fromString(id)).orElseThrow {RouteException.routeNotFound(id)}

    fun get(ids: List<UUID>): List<Route> = routeRepository.findAllById(ids)

    fun getAll(routeFilter: RouteFilter): List<Route> {
        return routeRepository.findByFilter(routeFilter)
    }

    fun delete(id: String) {
        return routeRepository.deleteById(UUID.fromString(id))
    }

    fun addComment(routeId: UUID, comment: Comment) {
        val optionalRoute = routeRepository.findById(routeId)

        if (optionalRoute.isPresent) {
            val route = optionalRoute.get()

            val updatedComments = route.comments.toMutableList().apply { add(comment) }
            val updatedRoute = route.copy(comments = updatedComments)

            routeRepository.save(updatedRoute)
        } else {
            throw RouteException.routeNotFound(routeId.toString())
        }
    }
}