package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.test.IntegrationDataTest
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.PageNotFoundException
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.CustomerRepository
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.test.createMockInstances
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import java.util.UUID
import javax.transaction.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@IntegrationDataTest
@Transactional
internal class CustomerDataProviderIntegrationTest {
    @Autowired
    private lateinit var provider: CustomerDataProvider

    @Autowired
    private lateinit var repository: CustomerRepository

    private val customerId = UUID.fromString("eca70c7e-8f0a-453a-904b-7a74351afd2a")
    private val email = "teste@teste.com"

    private fun createcustomers(n: Int = 10): List<Customer> {
        val customers = Customer::class.createMockInstances(n)
        return this.repository.saveAll(
            customers
                .map {
                    it
                        .toTable()
                        .copy(
                            name = "João da Silva"
                        )
                }
        )
            .map { it.toCore() }
    }

    @Test
    fun findById() {
        val customer = this
            .repository
            .save(
                Customer::class.createMockInstance()
                    .copy(id = this.customerId)
                    .toTable()
            )
            .toCore()

        val result = this.provider.findById(id = customer.id)

        assertNotNull(result)
        assertEquals(customer.id, result.id)
    }

    @Test
    fun `findById - cliente não encontrado`() {
        assertThrows<CustomerNotFoundException> {
            this.provider.findById(id = UUID.fromString("27ecd796-e154-49d6-877f-10432ce90698"))
        }
    }

    @Test
    fun findByEmail() {
        val customer = this
            .repository
            .save(
                Customer::class.createMockInstance()
                    .copy(email = this.email)
                    .toTable()
            )
            .toCore()

        val result = this.provider.findByEmail(email = this.email)

        assertNotNull(result)
        assertEquals(customer.email, result.email)
    }

    @Test
    fun findAll() {
        this.createcustomers(20)

        val page = PageRequest.of(0, 10)

        val result = this.provider.findAll(
            page = page
        )

        assertNotNull(result)
        assertTrue(result.content.size == 10)
        assertTrue(result.totalPages == 2)
        assertTrue(result.pageable.pageSize == 10)
        assertTrue(result.pageable.pageNumber == 0)
    }

    @Test
    fun `findAll - página não encontrada`() {
        val page = PageRequest.of(999999, 10)

        assertThrows<PageNotFoundException> {
            this.provider.findAll(page = page)
        }
    }

    @Test
    fun save() {
        val result = this.provider.save(Customer::class.createMockInstance())
        assertNotNull(result)
    }
}
