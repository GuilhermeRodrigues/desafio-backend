package com.luizalabs.desafio.provider.api.product

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.config.http.HttpClient
import com.luizalabs.desafio.config.http.HttpClientResponse
import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.provider.api.product.exception.ProductNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@UnitTest
internal class ProductApiProviderTest {
    @InjectMocks
    private lateinit var provider: ProductApiProvider

    @Mock
    private lateinit var client: HttpClient

    private fun toMockClientGet(parameter: String, statusCode: HttpStatus, body: String) {
        Mockito
            .`when`(this.client.get(path = "/product/$parameter"))
            .thenReturn(
                HttpClientResponse(
                    statusCode = statusCode,
                    body = body
                )
            )
    }

    @Test
    fun `buscar produtos - sucesso`() {
        val page = 1
        val parameter = "?page=$page"

        this.toMockClientGet(
            parameter,
            HttpStatus.OK,
            """
                {
                    "meta": {
                        "page_number": 1,
                        "page_size": 100
                    },
                    "products": [
                        {
                            "price": 1699.0,
                            "image": "http://challenge-api.luizalabs.com/images/1bf0f365-fbdd-4e21-9786-da459d78dd1f.jpg",
                            "brand": "bébé confort",
                            "id": "1bf0f365-fbdd-4e21-9786-da459d78dd1f",
                            "title": "Cadeira para Auto Iseos Bébé Confort Earth Brown"
                        },
                        {
                            "price": 1149.0,
                            "image": "http://challenge-api.luizalabs.com/images/958ec015-cfcf-258d-c6df-1721de0ab6ea.jpg",
                            "brand": "bébé confort",
                            "id": "958ec015-cfcf-258d-c6df-1721de0ab6ea",
                            "title": "Moisés Dorel Windoo 1529"
                        },
                        {
                            "price": 1149.0,
                            "image": "http://challenge-api.luizalabs.com/images/6a512e6c-6627-d286-5d18-583558359ab6.jpg",
                            "brand": "bébé confort",
                            "id": "6a512e6c-6627-d286-5d18-583558359ab6",
                            "title": "Moisés Dorel Windoo 1529"
                        }
                    ]
                }
                """
        )

        val response = this.provider.findAll(page)

        assertNotNull(response)
        assertEquals(response.products.first().id, UUID.fromString("1bf0f365-fbdd-4e21-9786-da459d78dd1f"))
        assertEquals(response.products.first().brand, "bébé confort")
        assertEquals(response.products.first().title, "Cadeira para Auto Iseos Bébé Confort Earth Brown")
    }

    @Test
    fun `buscar produtos - erro`() {
        val page = 1
        val parameter = "?page=$page"

        this.toMockClientGet(
            parameter,
            HttpStatus.NOT_FOUND,
            """
                {
                    "statusCode": 404,
                    "error": "Not Found",
                    "message": "Página não encontrada",
                    "code": null
                }
                """
        )

        assertThrows<PageNotFoundException> { this.provider.findAll(page) }
    }

    @Test
    fun `buscar produto pelo id - sucesso`() {
        val id = UUID.fromString("1bf0f365-fbdd-4e21-9786-da459d78dd1f")
        val parameter = "$id/"

        this.toMockClientGet(
            parameter,
            HttpStatus.OK,
            """
                {
                    "price": 1699.0,
                    "image": "http://challenge-api.luizalabs.com/images/1bf0f365-fbdd-4e21-9786-da459d78dd1f.jpg",
                    "brand": "bébé confort",
                    "id": "1bf0f365-fbdd-4e21-9786-da459d78dd1f",
                    "title": "Cadeira para Auto Iseos Bébé Confort Earth Brown"   
                }
                """
        )

        val response = this.provider.findById(id)

        assertNotNull(response)
        assertEquals(response.id, UUID.fromString("1bf0f365-fbdd-4e21-9786-da459d78dd1f"))
        assertEquals(response.brand, "bébé confort")
        assertEquals(response.title, "Cadeira para Auto Iseos Bébé Confort Earth Brown")
    }

    @Test
    fun `buscar produto pelo id - produto não encontrado`() {
        val id = UUID.fromString("1bf0f365-fbdd-4e21-9786-da459d78dd1f")
        val parameter = "$id/"

        this.toMockClientGet(
            parameter,
            HttpStatus.NOT_FOUND,
            """
                {
                    "statusCode": 404,
                    "error": "Not Found",
                    "message": "Produto não encontrado",
                    "code": null
                }
                """
        )

        assertThrows<ProductNotFoundException> { this.provider.findById(id) }
    }
}
