package org.example.controllers

import org.example.dto.CreateCommentRequest
import org.example.dto.RouteFilter
import org.example.dto.RouteCreateRequest
import org.example.dto.RouteUpdateRequest
import org.example.entities.Route
import org.example.mappers.toEntity
import org.example.services.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/route")
class RouteController(
    @Autowired private val routeService: RouteService,
) {

    @PostMapping("/save")
    fun saveRoute(@RequestBody routeRequest: RouteCreateRequest) {
        routeService.save(routeRequest.toEntity())
    }

    @PostMapping("/update")
    fun updateRoute(@RequestBody routeRequest: RouteUpdateRequest) {
        routeService.update(routeRequest.toEntity())
    }

    @GetMapping("/{id}")
    fun getRoute(@PathVariable id: String) : Route {
        return routeService.get(id)
    }

    @PostMapping("/addComment")
    fun addCommentToRoute(@RequestBody createCommentRequest: CreateCommentRequest) {
        routeService.addComment(
            createCommentRequest.routeId,
            createCommentRequest.toEntity()
        )
    }

    @PostMapping("/")
    fun getRoutes(@RequestBody routeFilter: RouteFilter) : Page<Route> {
        return routeService.getAll(routeFilter)
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: String) {
        return routeService.delete(id)
    }
}