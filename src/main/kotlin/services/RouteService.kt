package org.example.services

import org.example.dto.RouteFilter
import org.example.entities.Route
import org.example.exception.RouteException
import org.example.repositories.RouteRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class RouteService(private val repository: RouteRepository) {

    fun save(route: Route): Route {
        return repository.save(route)
    }

    fun update(route: Route): Route {
        repository.deleteById(route.id)
        return repository.save(route)
    }

    fun get(id: String): Route = repository.findById(UUID.fromString(id)).orElseThrow {RouteException("Route with id $id not found")}

    fun getAll(routeFilter: RouteFilter): Page<Route> {
        return repository.findAll(PageRequest.of(routeFilter.pageNumber, routeFilter.pageSize, Sort.by("title")))
    }

    fun delete(id: String) {
        return repository.deleteById(UUID.fromString(id))
    }
}