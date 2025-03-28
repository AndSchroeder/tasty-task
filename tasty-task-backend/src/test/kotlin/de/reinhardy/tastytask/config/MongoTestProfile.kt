package de.reinhardy.tastytask.config

import io.quarkus.test.junit.QuarkusTestProfile

class MongoTestProfile : QuarkusTestProfile {
    override fun getConfigOverrides(): Map<String, String> {
        return mapOf(
            "quarkus.mongodb.database" to "test-db",
            "quarkus.mongodb.devservices.enabled" to "true"
        )
    }
}