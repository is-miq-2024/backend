package org.example.repositories

import org.example.entities.Route
import org.example.entities.RouteType
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Duration
import java.util.UUID

interface RouteRepository : MongoRepository<Route, UUID> {
    fun findRoutesByTitle(title: String): List<Route>

    fun findByTypes(type: RouteType): List<Route>

    fun findRoutesByDifficulty(difficulty: Int): List<Route>

    fun findRoutesByDurationInMinutes(durationInMinutes: Duration): List<Route>

    fun findRoutesByRateGreaterThanEqual(minRate: Double): List<Route>
}