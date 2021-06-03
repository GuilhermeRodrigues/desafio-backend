package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.CustomerRepository
import com.luizalabs.desafio.provider.data.table.CustomerTable
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.test.createMockInstances
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@UnitTest
internal class CustomerDataProviderTest {
    @Mock
    private lateinit var repository: CustomerRepository

    @InjectMocks
    private lateinit var provider: CustomerDataProvider

    private val id = UUID.fromString("c35d6033-9ff9-447c-bd16-a9733919e729")

    @Test
    fun `lista todos os clientes`() {
        val page = PageRequest.of(1, 3)
        val customerPage = PageImpl(Customer::class.createMockInstances(3))

        Mockito
            .`when`(
                this.repository.findAll(pageable = page)
            )
            .thenReturn(customerPage.map { it.toTable() })

        val result = this.provider.findAll(page = page)

        assertEquals(customerPage.content, result.content)
        assertTrue(result.content.size == 3)
    }

    @Test
    fun `lista todos os clientes - página não encontrada`() {
        val page = PageRequest.of(1, 3)
        val customerPage = PageImpl(Customer::class.createMockInstances(0))

        Mockito
            .`when`(
                this.repository.findAll(pageable = page)
            )
            .thenReturn(customerPage.map { it.toTable() })

        assertThrows<PageNotFoundException> {
            this.provider.findAll(page = page)
        }
    }

    @Test
    fun `busca o cliente pelo id`() {
        val customer = Customer::class.createMockInstance()
            .copy(id = this.id)

        Mockito
            .`when`(
                this.repository.findById(this.id)
            )
            .thenReturn(Optional.of(customer.toTable()))

        val result = this.provider.findById(id = this.id)

        assertEquals(customer.id, result.id)
        assertEquals(customer.name, result.name)
        assertEquals(customer.email, result.email)
    }

    @Test
    fun `busca o cliente pelo id - cliente não encontrado`() {
        Mockito
            .`when`(
                this.repository.findById(this.id)
            )
            .thenReturn(Optional.empty())

        assertThrows<CustomerNotFoundException> {
            this.provider.findById(id = this.id)
        }
    }

    @Test
    fun `busca o cliente pelo email`() {
        val email = "teste@teste.com"

        val customer = Customer::class.createMockInstance()
            .copy(email = email)

        Mockito
            .`when`(
                this.repository.findByEmail(email)
            )
            .thenReturn(customer.toTable())

        val result = this.provider.findByEmail(email = email)

        assertEquals(customer.id, result!!.id)
        assertEquals(customer.name, result.name)
        assertEquals(customer.email, result.email)
    }

    @Test
    fun `salva a cliente`() {
        val customer = Customer::class.createMockInstance()
        val table = customer.toTable()

        Mockito.`when`(this.repository.save(anyObject(CustomerTable::class.java)))
            .thenReturn(table)

        val result = this.provider.save(customer)

        assertEquals(expected = customer, actual = result)
    }
}
