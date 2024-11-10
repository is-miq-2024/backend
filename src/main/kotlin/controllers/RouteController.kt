package org.example.controllers

import org.example.dto.RouteFilterDto
import org.example.dto.RouteCreateRequestDto
import org.example.dto.RouteUpdateRequestDto
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
    fun saveRoute(@RequestBody routeRequest: RouteCreateRequestDto) {
        routeService.save(routeRequestMapper.run { routeRequest.toDto() })
    }

    @PostMapping("/update")
    fun updateRoute(@RequestBody routeRequest: RouteUpdateRequestDto) {
        routeService.update(routeRequestMapper.run { routeRequest.toDto() })
    }

    @GetMapping("/{id}")
    fun getRoute(@PathVariable id: String) : Route {
        return  routeService.get(id)
    }

    @PostMapping("/")
    fun getRoutes(@RequestBody routeFilterDto: RouteFilterDto) : Page<Route> {
        return routeService.getAll(routeFilterDto)
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: String) {
        return routeService.delete(id)
    }
}