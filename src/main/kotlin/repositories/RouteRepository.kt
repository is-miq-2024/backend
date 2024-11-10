package org.example.repositories

import org.example.entities.Route
import org.example.entities.RouteType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.Duration
import java.util.*


@Repository
interface RouteRepository : MongoRepository<Route, UUID>, CriteriaRepository<Route> {
    fun findRoutesByTitle(title: String): List<Route>

    fun findByTypes(type: RouteType): List<Route>

    fun findRoutesByDifficulty(difficulty: Int): List<Route>

    fun findRoutesByDurationInMinutes(durationInMinutes: Duration): List<Route>

    fun findRoutesByRateGreaterThanEqual(minRate: Double): List<Route>
}