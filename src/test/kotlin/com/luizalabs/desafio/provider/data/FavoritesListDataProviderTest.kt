package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.FavoritesListRepository
import com.luizalabs.desafio.provider.data.table.FavoritesListTable
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals

@UnitTest
internal class FavoritesListDataProviderTest {
    @Mock
    private lateinit var repository: FavoritesListRepository

    @InjectMocks
    private lateinit var provider: FavoritesListDataProvider

    private val id = UUID.fromString("c35d6033-9ff9-447c-bd16-a9733919e729")

    @Test
    fun `busca a lista de favoritos pelo id do cliente`() {
        val favoritesList = FavoritesList::class.createMockInstance()
            .copy(id = this.id)

        Mockito
            .`when`(
                this.repository.findByCustomerId(this.id)
            )
            .thenReturn(favoritesList.toTable())

        val result = this.provider.findByCustomerId(customerId = this.id)

        assertEquals(favoritesList.id, result!!.id)
        assertEquals(favoritesList.customer.id, result.customer.id)
    }

    @Test
    fun `salva a lista de favoritos`() {
        val favoritesList = FavoritesList::class.createMockInstance()
        val table = favoritesList.toTable()

        Mockito.`when`(this.repository.save(anyObject(FavoritesListTable::class.java)))
            .thenReturn(table)

        val result = this.provider.save(favoritesList)

        assertEquals(expected = favoritesList, actual = result)
    }
}
