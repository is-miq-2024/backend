package org.example.repositories

import org.example.dto.RouteFilter
import org.example.entities.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import java.util.Optional

@Component
class CriteriaRepositoryImpl(@Autowired val mongoTemplate: MongoTemplate) : CriteriaRepository<Route> {

    override fun findByFilter(filter: RouteFilter): List<Route> {
        val query = Query()

        query.addCriteria(Criteria.where("title").regex(Optional.ofNullable(filter.title).orElse(""), "i"))

        if (filter.types != null && !CollectionUtils.isEmpty(filter.types)) {
            query.addCriteria(Criteria.where("types").all(filter.types.stream().map{x-> x.toString()}.toArray()))
        }

        if (filter.durationInMinutes != null) {
            query.addCriteria(Criteria.where("durationInMinutes")
                .lte(Optional.ofNullable(filter.durationInMinutes.upperBound)
                .orElse(Long.MAX_VALUE))
                .gte(Optional.ofNullable(filter.durationInMinutes.lowerBound)
                .orElse(Long.MIN_VALUE)))
        }

        if (filter.rate != null) {
            query.addCriteria(Criteria.where("rate")
                .lte(Optional.ofNullable(filter.rate.upperBound)
                .orElse(MAX_RATE))
                .gte(Optional.ofNullable(filter.rate.lowerBound)
                .orElse(MIN_RATE)))
        }

        if (filter.difficulty != null) {
            query.addCriteria(Criteria.where("difficulty")
                .lte(Optional.ofNullable(filter.difficulty.upperBound)
                .orElse(MAX_DIFFICULTY))
                .gte(Optional.ofNullable(filter.difficulty.lowerBound)
                .orElse(MIN_DIFFICULTY)))
        }

        query.with(PageRequest.of(filter.pageNumber, filter.pageSize))

        return mongoTemplate.find(query, Route::class.java)
    }

    companion object {
        const val MIN_RATE  = 0.0
        const val MAX_RATE  = 5.0
        const val MIN_DIFFICULTY  = 0
        const val MAX_DIFFICULTY = 10
    }
}
