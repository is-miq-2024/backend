package org.example.controllers

import org.example.dto.RouteFilter
import org.example.dto.RouteCreateRequest
import org.example.dto.RouteUpdateRequest
import org.example.entities.Route
import org.example.mappers.RouteRequestMapper
import org.example.services.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/route")
class RouteController(@Autowired private val routeService: RouteService, @Autowired private val routeRequestMapper: RouteRequestMapper) {

    @PostMapping("/save")
    fun saveRoute(@RequestBody routeRequest: RouteCreateRequest) {
        routeService.save(routeRequestMapper.run { routeRequest.toDto() })
    }

    @PostMapping("/update")
    fun updateRoute(@RequestBody routeRequest: RouteUpdateRequest) {
        routeService.update(routeRequestMapper.run { routeRequest.toDto() })
    }

    @GetMapping("/{id}")
    fun getRoute(@PathVariable id: String) : Route {
        return  routeService.get(id)
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