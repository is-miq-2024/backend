package org.example.configurations

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.bson.UuidRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["org.example.repositories"])
class MongoConfig {

    @Value("\${spring.data.mongodb.connection-uri}")
    private val mongoUri: String = ""

    @Bean
    fun mongoClient(): MongoClient {
         return MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(mongoUri))
                .uuidRepresentation(UuidRepresentation.STANDARD).build())
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient?): MongoTemplate {
        return MongoTemplate(mongoClient!!, "travel-db")
    }
}