package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.dto.CustomerCreateDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.EmailAlreadyUsedException
import com.luizalabs.desafio.core.gateway.CustomerFindByEmailGateway
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertEquals

@UnitTest
internal class CustomerCreateUseCaseTest {
    @Mock
    private lateinit var customerSaveGateway: CustomerSaveGateway

    @Mock
    private lateinit var customerFindByEmailGateway: CustomerFindByEmailGateway

    @InjectMocks
    private lateinit var customerCreateUseCase: CustomerCreateUseCase

    private val name = "João da Silva"
    private val email = "teste@teste.com"
    private val customerCreateDto = CustomerCreateDto::class.createMockInstance()
        .copy(
            name = name,
            email = email
        )

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerSaveGateway, Mockito.times(1))
            .save(customer = anyObject())

        Mockito
            .verify(this.customerFindByEmailGateway, Mockito.times(1))
            .findByEmail(email = Mockito.anyString())
    }

    @Test
    fun `Salvar o cliente com sucesso`() {
        val customer = Customer::class.createMockInstance().copy(
            name = this.name,
            email = this.email
        )

        Mockito
            .`when`(this.customerFindByEmailGateway.findByEmail(email = this.email))
            .thenReturn(null)

        Mockito
            .`when`(this.customerSaveGateway.save(anyObject()))
            .thenReturn(customer)

        val result = this.customerCreateUseCase.execute(this.customerCreateDto)

        this.verifyAllMethodsCalled()
        assertEquals(customer.name, result.name)
        assertEquals(customer.email, result.email)
    }

    @Test
    fun `Salvar o cliente com erro de e-mail já utilizado`() {
        val customer = Customer::class.createMockInstance().copy(
            name = this.name,
            email = this.email
        )

        Mockito
            .`when`(this.customerFindByEmailGateway.findByEmail(email = this.email))
            .thenReturn(customer)

        assertThrows<EmailAlreadyUsedException> {
            this.customerCreateUseCase.execute(this.customerCreateDto)
        }
    }
}
