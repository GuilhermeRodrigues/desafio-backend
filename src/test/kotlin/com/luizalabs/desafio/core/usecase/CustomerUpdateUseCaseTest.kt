package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.dto.CustomerUpdateDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals

@UnitTest
internal class CustomerUpdateUseCaseTest {
    @Mock
    private lateinit var customerSaveGateway: CustomerSaveGateway

    @Mock
    private lateinit var customerFindByEmailGateway: CustomerFindByEmailGateway

    @Mock
    private lateinit var customerFindByIdGateway: CustomerFindByIdGateway

    @InjectMocks
    private lateinit var customerUpdateUseCase: CustomerUpdateUseCase

    private val id = UUID.fromString("4d2377c8-2bcb-4927-b837-9ab04ff28f79")
    private val name = "João da Silva"
    private val newName = "José da Silva"
    private val email = "teste@teste.com"
    private val newEmail = "joao@teste.com"

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerSaveGateway, Mockito.times(1))
            .save(customer = anyObject())

        Mockito
            .verify(this.customerFindByEmailGateway, Mockito.times(1))
            .findByEmail(email = this.newEmail)

        Mockito
            .verify(this.customerFindByIdGateway, Mockito.times(1))
            .findById(id = this.id)
    }

    @Test
    fun `Atualizar o cliente com sucesso`() {
        val customer = Customer::class.createMockInstance().copy(
            name = this.name,
            email = this.email
        )

        val customerUpdateDto = CustomerUpdateDto::class.createMockInstance().copy(
            email = this.newEmail
        )

        val newCustomer = customer.copy(
            email = this.newEmail
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenReturn(customer)

        Mockito
            .`when`(this.customerFindByEmailGateway.findByEmail(email = customerUpdateDto.email!!))
            .thenReturn(null)

        Mockito
            .`when`(this.customerSaveGateway.save(anyObject(Customer::class.java)))
            .thenReturn(newCustomer)

        val result = this.customerUpdateUseCase.execute(this.id, customerUpdateDto)

        this.verifyAllMethodsCalled()
        assertEquals(customerUpdateDto.email, result.email)
    }

    @Test
    fun `Atualizar o cliente com sucesso (e-mail nulo)`() {
        val customer = Customer::class.createMockInstance().copy(
            name = this.name,
            email = this.email
        )

        val customerUpdateDto = CustomerUpdateDto(
            name = this.newName,
            email = null
        )

        val newCustomer = customer.copy(
            name = this.newName
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenReturn(customer)

        Mockito
            .`when`(this.customerFindByEmailGateway.findByEmail(email = ""))
            .thenReturn(null)

        Mockito
            .`when`(this.customerSaveGateway.save(anyObject(Customer::class.java)))
            .thenReturn(newCustomer)

        val result = this.customerUpdateUseCase.execute(this.id, customerUpdateDto)

        assertEquals(customerUpdateDto.name, result.name)
    }

    @Test
    fun `Atualizar o cliente com erro de cliente não encontrado`() {
        val customerUpdateDto = CustomerUpdateDto::class.createMockInstance()

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenThrow(CustomerNotFoundException())

        assertThrows<CustomerNotFoundException> {
            this.customerUpdateUseCase.execute(this.id, customerUpdateDto)
        }
    }

    @Test
    fun `Atualizar o cliente com erro de e-mail já utilizado`() {
        val customer = Customer::class.createMockInstance().copy(
            name = this.name,
            email = this.email
        )

        val customerUpdateDto = CustomerUpdateDto::class.createMockInstance().copy(
            email = this.newEmail
        )

        val newCustomer = customer.copy(
            email = this.newEmail
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.id))
            .thenReturn(customer)

        Mockito
            .`when`(this.customerFindByEmailGateway.findByEmail(email = customerUpdateDto.email!!))
            .thenReturn(newCustomer)

        assertThrows<EmailAlreadyUsedException> {
            this.customerUpdateUseCase.execute(this.id, customerUpdateDto)
        }
    }
}
