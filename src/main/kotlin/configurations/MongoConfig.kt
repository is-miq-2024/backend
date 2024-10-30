package org.example.configurations

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
class MongoConfig {
    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create("mongodb://mongoadmin:secret@localhost:27017")
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient?): MongoTemplate {
        return MongoTemplate(mongoClient!!, "testdb")
    }
}