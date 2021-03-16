package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindAllGateway
import com.luizalabs.desafio.util.test.createMockInstances
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import kotlin.test.assertNotNull

@UnitTest
internal class CustomerFindAllUseCaseTest {
    @Mock
    private lateinit var customerFindAllGateway: CustomerFindAllGateway

    @InjectMocks
    private lateinit var customerFindAllUseCase: CustomerFindAllUseCase

    private val page = PageRequest.of(1, 3)

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerFindAllGateway, Mockito.times(1))
            .findAll(page = this.page)
    }

    @Test
    fun `Listar os clientes com sucesso`() {
        val customerPage = PageImpl(Customer::class.createMockInstances(1))

        Mockito
            .`when`(this.customerFindAllGateway.findAll(page = this.page))
            .thenReturn(customerPage)

        val result = this.customerFindAllUseCase.execute(this.page)

        this.verifyAllMethodsCalled()
        assertNotNull(result.content)
    }

    @Test
    fun `Listar os clientes com erro de página não encontrada`() {
        Mockito
            .`when`(this.customerFindAllGateway.findAll(page = this.page))
            .thenThrow(PageNotFoundException())

        assertThrows<PageNotFoundException> {
            this.customerFindAllUseCase.execute(this.page)
        }
    }
}
