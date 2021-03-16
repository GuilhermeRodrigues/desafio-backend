package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.util.anyObject
import com.luizalabs.desafio.util.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertNotNull

@UnitTest
internal class CustomerDeleteUseCaseTest {
    @Mock
    private lateinit var customerSaveGateway: CustomerSaveGateway

    @Mock
    private lateinit var customerFindByIdGateway: CustomerFindByIdGateway

    @InjectMocks
    private lateinit var customerDeleteUseCase: CustomerDeleteUseCase

    private val id = UUID.fromString("4d2377c8-2bcb-4927-b837-9ab04ff28f79")
    private val name = "João da Silva"
    private val email = "teste@teste.com"

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerSaveGateway, Mockito.times(1))
            .save(customer = anyObject())

        Mockito
            .verify(this.customerFindByIdGateway, Mockito.times(1))
            .findById(id = this.id)
    }

    @Test
    fun `Excluir o cliente com sucesso`() {
        val customer = Customer::class.createMockInstance().copy(
            name = this.name,
            email = this.email
        )

        val newCustomer = customer.copy(
            deletedAt = LocalDateTime.now()
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenReturn(customer)

        Mockito
            .`when`(this.customerSaveGateway.save(anyObject(Customer::class.java)))
            .thenReturn(newCustomer)

        val result = this.customerDeleteUseCase.execute(this.id)

        this.verifyAllMethodsCalled()
        assertNotNull(result.deletedAt)
    }

    @Test
    fun `Excluir o cliente com erro de cliente não encontrado`() {
        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenThrow(CustomerNotFoundException())

        assertThrows<CustomerNotFoundException> {
            this.customerDeleteUseCase.execute(this.id)
        }
    }
}
