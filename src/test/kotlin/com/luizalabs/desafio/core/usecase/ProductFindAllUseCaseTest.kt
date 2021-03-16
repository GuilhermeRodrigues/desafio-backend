package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.gateway.ProductFindAllGateway
import com.luizalabs.desafio.provider.api.product.exception.ProductInternalServerErrorException
import com.luizalabs.desafio.provider.api.product.response.ProductResponse
import com.luizalabs.desafio.util.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertNotNull

@UnitTest
internal class ProductFindAllUseCaseTest {
    @Mock
    private lateinit var productFindAllGateway: ProductFindAllGateway

    @InjectMocks
    private lateinit var productFindAllUseCase: ProductFindAllUseCase

    private val page = 1

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.productFindAllGateway, Mockito.times(1))
            .findAll(page = this.page)
    }

    @Test
    fun `Listar os produtos com sucesso`() {
        val productResponse = ProductResponse::class.createMockInstance()

        Mockito
            .`when`(this.productFindAllGateway.findAll(page = this.page))
            .thenReturn(productResponse)

        val result = this.productFindAllUseCase.execute(this.page)

        this.verifyAllMethodsCalled()
        assertNotNull(result.products)
    }

    @Test
    fun `Listar os produtos com erro interno`() {
        Mockito
            .`when`(this.productFindAllGateway.findAll(page = this.page))
            .thenThrow(ProductInternalServerErrorException())

        assertThrows<ProductInternalServerErrorException> {
            this.productFindAllUseCase.execute(this.page)
        }
    }
}
