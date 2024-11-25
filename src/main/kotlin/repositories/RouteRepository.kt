package org.example.repositories

import org.example.entities.Route
import org.example.entities.RouteType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface RouteRepository : MongoRepository<Route, UUID>, FilteringRepository<Route> {
    fun findByTypes(type: RouteType): List<Route>
}