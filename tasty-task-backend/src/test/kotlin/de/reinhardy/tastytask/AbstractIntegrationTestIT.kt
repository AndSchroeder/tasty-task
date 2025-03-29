package de.reinhardy.tastytask

import com.mongodb.client.MongoClient
import de.reinhardy.tastytask.config.MongoTestProfile
import io.quarkus.test.junit.TestProfile
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach

@TestProfile(MongoTestProfile::class)
abstract class AbstractIntegrationTestIT() {

    @Inject
    lateinit var mongoClient: MongoClient

    companion object {
        val COLLECTIONS = listOf(
            "recipes"
        )
    }

    @BeforeEach
    fun setup() {
        COLLECTIONS.forEach { collection ->
            mongoClient.getDatabase("test-db").getCollection(collection).drop()
        }
    }

    fun getRequest(path: String) = given().`when`().get(path)
    fun deleteRequest(path: String) = given().`when`().delete(path)
    fun <T>postRequest(path: String, requestBody: T) =
        given().contentType(ContentType.JSON).body(requestBody).`when`().post(path)

    fun Response.shouldSucceed() = this.then().statusCode(200)
    fun Response.shouldBeCreated() = this.then().statusCode(201)
    fun Response.shouldBeBadRequest() = this.then().statusCode(400)
    fun Response.shouldBeForbidden() = this.then().statusCode(401)
    fun Response.shouldBeUnautorized() = this.then().statusCode(403)
    fun Response.shouldBeNotFound() = this.then().statusCode(404)

    inline fun <reified T> Response.shouldSucceedAndReturn(): T = this.shouldSucceed().andReturn<T>()
    inline fun <reified T> Response.shouldBeCreatedAndReturn(): T = this.shouldBeCreated().andReturn<T>()

    inline fun <reified T> ValidatableResponse.andReturn(): T =
        this.extract()
            .body()
            .`as`(object : TypeRef<T>() {})

}