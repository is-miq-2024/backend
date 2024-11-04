package org.example.configurations

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
class MongoConfig {

    @Value("\${spring.data.mongodb.connection-uri}")
    private val mongoUri: String = ""

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create(mongoUri)
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient?): MongoTemplate {
        return MongoTemplate(mongoClient!!, "travel-db")
    }
}
