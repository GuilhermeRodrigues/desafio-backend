package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.FavoriteRepository
import com.luizalabs.desafio.provider.data.table.FavoriteTable
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals

@UnitTest
internal class FavoriteDataProviderTest {
    @Mock
    private lateinit var repository: FavoriteRepository

    @InjectMocks
    private lateinit var provider: FavoriteDataProvider

    private val id = UUID.fromString("c35d6033-9ff9-447c-bd16-a9733919e729")

    @Test
    fun `busca o favorito pelo id da lista de favoritos`() {
        val favorite = Favorite::class.createMockInstance()
            .copy(id = this.id)

        Mockito
            .`when`(
                this.repository.findByFavoritesListIdAndDeletedAtIsNull(this.id)
            )
            .thenReturn(listOf(favorite.toTable()))

        val result = this.provider.findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = this.id)

        assertEquals(favorite.id, result!!.first().id)
        assertEquals(favorite.favoritesList.id, result.first().favoritesList.id)
    }

    @Test
    fun `salva a favorito`() {
        val favorite = Favorite::class.createMockInstance()
        val table = favorite.toTable()

        Mockito.`when`(this.repository.save(anyObject(FavoriteTable::class.java)))
            .thenReturn(table)

        val result = this.provider.save(favorite)

        assertEquals(expected = favorite, actual = result)
    }
}
