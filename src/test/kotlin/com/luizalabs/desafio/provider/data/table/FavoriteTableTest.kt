package com.luizalabs.desafio.provider.data.table

import com.luizalabs.desafio.util.test.assertSerializable
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class FavoriteTableTest {
    @Test
    fun serializable1() {
        FavoriteTable::class.assertSerializable()
        val table = FavoriteTable::class.createMockInstance()
        assertNotNull(table)
    }

    @Test
    fun serializable2() {
        val table = FavoriteTable::class.createMockInstance(useDefaultValues = true, generateNulls = false)
        assertNotNull(table)
    }
}
