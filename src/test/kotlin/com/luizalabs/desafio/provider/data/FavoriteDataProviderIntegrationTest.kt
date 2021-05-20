package com.luizalabs.desafio.provider.data

import com.luizalabs.desafio.annotation.test.IntegrationDataTest
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.mapper.toCore
import com.luizalabs.desafio.mapper.toTable
import com.luizalabs.desafio.provider.data.repository.CustomerRepository
import com.luizalabs.desafio.provider.data.repository.FavoriteRepository
import com.luizalabs.desafio.provider.data.repository.FavoritesListRepository
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import javax.transaction.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@IntegrationDataTest
@Transactional
internal class FavoriteDataProviderIntegrationTest {
    @Autowired
    private lateinit var customerDataProvider: CustomerDataProvider

    @Autowired
    private lateinit var favoritesListDataProvider: FavoritesListDataProvider

    @Autowired
    private lateinit var favoriteDataProvider: FavoriteDataProvider

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var favoritesListRepository: FavoritesListRepository

    @Autowired
    private lateinit var favoriteRepository: FavoriteRepository

    @Test
    fun save() {
        val customer = this.customerDataProvider.save(Customer::class.createMockInstance())

        val favoritesList = this.favoritesListDataProvider.save(
            FavoritesList::class.createMockInstance()
                .copy(customer = customer)
        )

        val result = this.favoriteDataProvider.save(
            Favorite::class.createMockInstance()
                .copy(favoritesList = favoritesList)
        )
        assertNotNull(result)
    }

    @Test
    fun findByFavoritesListIdAndDeletedAtIsNull() {
        val customer = this
            .customerRepository
            .save(
                Customer::class.createMockInstance()
                    .toTable()
            )
            .toCore()

        val favoritesList = this
            .favoritesListRepository
            .save(
                FavoritesList::class.createMockInstance()
                    .copy(customer = customer)
                    .toTable()
            )
            .toCore()

        val favorite = this.favoriteRepository
            .save(
                Favorite::class.createMockInstance()
                    .copy(favoritesList = favoritesList)
                    .toTable()
            )
            .toCore()

        val result = this.favoriteDataProvider.findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)

        assertNotNull(result)
        assertEquals(favorite.id, result.first().id)
    }
}
