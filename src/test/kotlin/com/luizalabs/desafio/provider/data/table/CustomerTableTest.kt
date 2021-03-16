package com.luizalabs.desafio.provider.data.table

import com.luizalabs.desafio.util.test.assertSerializable
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class CustomerTableTest {
    @Test
    fun serializable1() {
        CustomerTable::class.assertSerializable()
        val table = CustomerTable::class.createMockInstance()
        assertNotNull(table)
    }

    @Test
    fun serializable2() {
        val table = CustomerTable::class.createMockInstance(useDefaultValues = true, generateNulls = false)
        assertNotNull(table)
    }
}
