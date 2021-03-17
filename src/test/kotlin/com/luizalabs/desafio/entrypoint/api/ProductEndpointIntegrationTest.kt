package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.EndpointTest
import com.luizalabs.desafio.core.interactor.ProductFindAllInteractor
import com.luizalabs.desafio.core.interactor.ProductFindByIdInteractor
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.toObject
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class ProductEndpointIntegrationTest : EndpointTest() {

    @MockBean
    private lateinit var productFindAllInteractor: ProductFindAllInteractor

    @MockBean
    private lateinit var productFindByIdInteractor: ProductFindByIdInteractor

    @Test
    fun `product endpoint (get) - find all`() {
        val productResponse = ProductResponse::class.createMockInstance()

        Mockito
            .`when`(this.productFindAllInteractor.execute(1))
            .thenReturn(productResponse)

        val restAssured = this.restAssured()
            .`when`()
            .queryParam("_page", "1")
            .get("/v1/products")

        val result = restAssured
            .body
            .asString()
            .toObject(productResponse::class)

        assertNotNull(result.products)

        restAssured
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `product endpoint (get) - find by id`() {
        val id = UUID.fromString("7edc246c-e8cb-48f6-8fb7-b7a333c8a23c")

        val product = Product::class.createMockInstance()
            .copy(
                id = id
            )

        Mockito
            .`when`(this.productFindByIdInteractor.execute(id))
            .thenReturn(product)

        val restAssured = this.restAssured()
            .`when`()
            .get("/v1/products/${product.id}")

        val result = restAssured
            .body
            .asString()
            .toObject(product::class)

        assertEquals(product.id, result.id)
        assertEquals(product.title, result.title)
        assertEquals(product.brand, result.brand)
        assertEquals(product.price, result.price)
        assertEquals(product.image, result.image)

        restAssured
            .then()
            .assertThat()
            .statusCode(200)
    }
}
