package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.interactor.ProductFindAllInteractor
import com.luizalabs.desafio.core.interactor.ProductFindByIdInteractor
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@UnitTest
internal class ProductEndpointTest {
    @InjectMocks
    private lateinit var productEndpoint: ProductEndpoint

    @Mock
    private lateinit var productFindAllInteractor: ProductFindAllInteractor

    @Mock
    private lateinit var productFindByIdInteractor: ProductFindByIdInteractor

    private val customerId = UUID.fromString("a9e4fcbf-0bb4-498a-b652-cb12679f965a")

    private val productId = UUID.fromString("63b1c0af-183c-44f7-b0f1-2bab91aed3f3")

    @Test
    fun `product endpoint (get) - findById`() {
        val product = Product::class.createMockInstance().copy(id = this.customerId)

        Mockito.`when`(this.productFindByIdInteractor.execute(this.productId))
            .thenReturn(product)

        val result = this.productEndpoint.findById(this.productId)

        assertEquals(product.id, result.id)
        assertEquals(product.brand, result.brand)
        assertEquals(product.title, result.title)
    }

    @Test
    fun `product endpoint (get) - findAll`() {
        val page = 1

        val productResponse = ProductResponse::class.createMockInstance()

        Mockito.`when`(this.productFindAllInteractor.execute(page))
            .thenReturn(productResponse)

        val result = this.productEndpoint.findAll(page)

        assertNotNull(result.products)
    }
}
