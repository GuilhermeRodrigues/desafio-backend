package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.provider.data.table.CustomerTable
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class CustomerMapperTest {
    @Test
    fun toCore() {
        val table = CustomerTable::class.createMockInstance()
        assertNotNull(table.toCore())
    }

    @Test
    fun toTable() {
        val entity = Customer::class.createMockInstance()
        assertNotNull(entity.toTable())
    }

    @Test
    fun toCustomerResponse() {
        val entity = Customer::class.createMockInstance()
        assertNotNull(entity.toCustomerResponse())
    }
}
