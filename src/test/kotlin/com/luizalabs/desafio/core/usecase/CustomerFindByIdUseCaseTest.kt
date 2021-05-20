package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertNotNull

@UnitTest
internal class CustomerFindByIdUseCaseTest {
    @Mock
    private lateinit var customerFindByIdGateway: CustomerFindByIdGateway

    @InjectMocks
    private lateinit var customerFindByIdUseCase: CustomerFindByIdUseCase

    private val id = UUID.fromString("4d2377c8-2bcb-4927-b837-9ab04ff28f79")

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerFindByIdGateway, Mockito.times(1))
            .findById(id = this.id)
    }

    @Test
    fun `Buscar o cliente pelo id com sucesso`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.id
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenReturn(customer)

        val result = this.customerFindByIdUseCase.execute(this.id)

        this.verifyAllMethodsCalled()
        assertNotNull(result.id)
        assertNotNull(result.name)
        assertNotNull(result.email)
    }

    @Test
    fun `Buscar o cliente pelo id com erro de cliente não encontrado`() {
        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenThrow(CustomerNotFoundException())

        assertThrows<CustomerNotFoundException> {
            this.customerFindByIdUseCase.execute(this.id)
        }
    }
}
