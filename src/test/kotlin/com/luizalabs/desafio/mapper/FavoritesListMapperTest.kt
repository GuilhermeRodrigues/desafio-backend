package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.provider.data.table.FavoritesListTable
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class FavoritesListMapperTest {
    @Test
    fun toCore() {
        val table = FavoritesListTable::class.createMockInstance()
        assertNotNull(table.toCore())
    }

    @Test
    fun toTable() {
        val entity = FavoritesList::class.createMockInstance()
        assertNotNull(entity.toTable())
    }
}
