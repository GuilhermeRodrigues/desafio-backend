package com.luizalabs.desafio.mapper

import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.provider.data.table.FavoriteTable
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.test.createMockInstances
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class FavoriteMapperTest {
    @Test
    fun toCore() {
        val table = FavoriteTable::class.createMockInstance()
        assertNotNull(table.toCore())
    }

    @Test
    fun toTable() {
        val entity = Favorite::class.createMockInstance()
        assertNotNull(entity.toTable())
    }

    @Test
    fun toFavoriteResponse() {
        val favorites = Product::class.createMockInstances(3)
        val entity = Favorite::class.createMockInstance()
        assertNotNull(entity.toFavoriteResponse(favorites))
    }
}
