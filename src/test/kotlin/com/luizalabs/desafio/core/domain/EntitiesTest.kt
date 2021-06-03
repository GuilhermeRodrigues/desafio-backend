package com.luizalabs.desafio.core.domain

import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.test.testDataClassesByPackage
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertNotNull

internal class EntitiesTest {

    @Test
    fun testDataClassesByPackage() {
        testDataClassesByPackage("com.luizalabs.desafio.core.domain")
    }

    @Test
    fun `customer updatedAt`() {
        val table = Customer::class.createMockInstance(useDefaultValues = true, generateNulls = false).copy(
            updatedAt = null
        )
        table.updatedAt = LocalDateTime.now()
        assertNotNull(table)
    }

    @Test
    fun `customer deletedAt`() {
        val table = Customer::class.createMockInstance(useDefaultValues = true, generateNulls = false).copy(
            deletedAt = null
        )
        table.deletedAt = LocalDateTime.now()
        assertNotNull(table)
    }

    @Test
    fun `favorite deletedAt`() {
        val table = Favorite::class.createMockInstance(useDefaultValues = true, generateNulls = false).copy(
            deletedAt = null
        )
        table.deletedAt = LocalDateTime.now()
        assertNotNull(table)
    }
}
