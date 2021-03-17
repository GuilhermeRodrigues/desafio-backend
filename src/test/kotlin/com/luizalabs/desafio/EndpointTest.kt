package com.luizalabs.desafio

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test", "api")
abstract class EndpointTest {
    @LocalServerPort
    private var port: Int? = null

    @BeforeEach
    fun setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.port = port!!
    }

    protected fun restAssured(): RequestSpecification {
        return RestAssured
            .given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .header(Header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.nNVDiMPBQeRcUBs02wR7p-WAnDpV5XJoNexMkQDUhKY"))
    }
}
