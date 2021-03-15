package com.luizalabs.desafio.provider.data.table

import com.luizalabs.desafio.util.assertSerializable
import com.luizalabs.desafio.util.createMockInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class FavoritesListTableTest {
    @Test
    fun serializable1() {
        FavoritesListTable::class.assertSerializable()
        val table = FavoritesListTable::class.createMockInstance()
        assertNotNull(table)
    }

    @Test
    fun serializable2() {
        val table = FavoritesListTable::class.createMockInstance(useDefaultValues = true, generateNulls = false)
        assertNotNull(table)
    }
}
