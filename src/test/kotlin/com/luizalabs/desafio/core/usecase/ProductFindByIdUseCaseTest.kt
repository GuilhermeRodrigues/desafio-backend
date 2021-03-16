package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.gateway.ProductFindByIdGateway
import com.luizalabs.desafio.provider.api.product.exception.ProductNotFoundException
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals

@UnitTest
internal class ProductFindByIdUseCaseTest {
    @Mock
    private lateinit var productFindByIdGateway: ProductFindByIdGateway

    @InjectMocks
    private lateinit var productFindByIdUseCase: ProductFindByIdUseCase

    private val id = UUID.fromString("fc37fd22-6ffa-4b7d-8ca7-a390f5c2fb49")

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.productFindByIdGateway, Mockito.times(1))
            .findById(id = this.id)
    }

    @Test
    fun `Buscar um produto pelo id com sucesso`() {
        val product = Product::class.createMockInstance()
            .copy(id = this.id)

        Mockito
            .`when`(this.productFindByIdGateway.findById(id = this.id))
            .thenReturn(product)

        val result = this.productFindByIdUseCase.execute(this.id)

        this.verifyAllMethodsCalled()
        assertEquals(product.id, result.id)
        assertEquals(product.brand, result.brand)
        assertEquals(product.title, result.title)
    }

    @Test
    fun `Buscar um produto pelo id com erro de produto não encontrado`() {
        Mockito
            .`when`(this.productFindByIdGateway.findById(id = this.id))
            .thenThrow(ProductNotFoundException())

        assertThrows<ProductNotFoundException> {
            this.productFindByIdUseCase.execute(this.id)
        }
    }
}
