package org.example.services

import org.example.dto.RouteFilter
import org.example.entities.Comment
import org.example.entities.Route
import org.example.exception.RouteException
import org.example.repositories.RouteRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class RouteService(private val routeRepository: RouteRepository) {

    fun save(route: Route): Route {
        return routeRepository.save(route)
    }

    fun update(route: Route): Route {
        routeRepository.deleteById(route.id)
        return routeRepository.save(route)
    }

    fun get(id: String): Route = routeRepository.findById(UUID.fromString(id)).orElseThrow {RouteException("Route with id $id not found")}

    fun getAll(routeFilter: RouteFilter): Page<Route> {
        return routeRepository.findAll(PageRequest.of(routeFilter.pageNumber, routeFilter.pageSize, Sort.by("title")))
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
            throw RouteException("Route with ID $routeId not found")
        }
    }
}