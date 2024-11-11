package org.example.repositories

import org.example.dto.RouteFilter
import org.example.entities.Route

interface FilteringRepository<T> {

    fun findByFilter(filter: RouteFilter): List<Route>
}