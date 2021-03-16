package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UnitTest
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.domain.Favorite
import com.luizalabs.desafio.core.domain.FavoritesList
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoriteSaveGateway
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.entrypoint.api.request.CustomerFavoriteRequest
import com.luizalabs.desafio.util.anyObject
import com.luizalabs.desafio.util.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@UnitTest
internal class CustomerFavoriteRemoveUseCaseTest {
    @Mock
    private lateinit var customerFindByIdGateway: CustomerFindByIdGateway

    @Mock
    private lateinit var favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway

    @Mock
    private lateinit var favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway

    @Mock
    private lateinit var favoriteSaveGateway: FavoriteSaveGateway

    @InjectMocks
    private lateinit var customerFavoriteRemoveUseCase: CustomerFavoriteRemoveUseCase

    private val customerId = UUID.fromString("3c1980ab-ab8a-4bd8-af2b-e47874380209")
    private val favoritesListId = UUID.fromString("fc6ac9ee-85bb-4591-9360-ccc5f606a926")

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerFindByIdGateway, Mockito.times(1))
            .findById(id = this.customerId)

        Mockito
            .verify(this.favoritesListFindByCustomerIdGateway, Mockito.times(1))
            .findByCustomerId(customerId = this.customerId)

        Mockito
            .verify(this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway, Mockito.times(1))
            .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = this.favoritesListId)

        Mockito
            .verify(this.favoriteSaveGateway, Mockito.times(1))
            .save(favorite = anyObject())
    }

    @Test
    fun `Remover o favorito do cliente com sucesso`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId
        )

        val favoritesList = FavoritesList::class.createMockInstance().copy(
            id = this.favoritesListId,
            customer = customer
        )

        val favorite = Favorite::class.createMockInstance().copy(
            favoritesList = favoritesList
        )

        val newFavorite = favorite.copy(
            deletedAt = LocalDateTime.now()
        )

        val customerFavoriteRequest = CustomerFavoriteRequest::class.createMockInstance().copy(
            productId = newFavorite.product.id
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenReturn(customer)

        Mockito
            .`when`(
                this.favoritesListFindByCustomerIdGateway
                    .findByCustomerId(customerId = customer.id)
            )
            .thenReturn(favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(listOf(favorite))

        Mockito
            .`when`(this.favoriteSaveGateway.save(anyObject(Favorite::class.java)))
            .thenReturn(newFavorite)

        val result = this.customerFavoriteRemoveUseCase.execute(this.customerId, customerFavoriteRequest)

        this.verifyAllMethodsCalled()
        assertEquals(result.id, favorite.id)
        assertNotNull(result.deletedAt)
    }

    @Test
    fun `Remover o favorito do cliente com erro de cliente não encontrado`() {
        val customerFavoriteRequest = CustomerFavoriteRequest::class.createMockInstance()

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenThrow(CustomerNotFoundException())

        assertThrows<CustomerNotFoundException> {
            this.customerFavoriteRemoveUseCase.execute(this.customerId, customerFavoriteRequest)
        }
    }

    @Test
    fun `Remover o favorito do cliente com erro de favorito não encontrado`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId
        )

        val customerFavoriteRequest = CustomerFavoriteRequest::class.createMockInstance()

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenReturn(customer)

        Mockito
            .`when`(
                this.favoritesListFindByCustomerIdGateway
                    .findByCustomerId(customerId = customer.id)
            )
            .thenReturn(null)

        assertThrows<FavoriteNotFoundException> {
            this.customerFavoriteRemoveUseCase.execute(this.customerId, customerFavoriteRequest)
        }
    }

    @Test
    fun `Remover o favorito do cliente com erro de favorito não encontrado 2`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId
        )

        val favoritesList = FavoritesList::class.createMockInstance().copy(
            id = this.favoritesListId,
            customer = customer
        )

        val customerFavoriteRequest = CustomerFavoriteRequest::class.createMockInstance()

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenReturn(customer)

        Mockito
            .`when`(
                this.favoritesListFindByCustomerIdGateway
                    .findByCustomerId(customerId = customer.id)
            )
            .thenReturn(favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(null)

        assertThrows<FavoriteNotFoundException> {
            this.customerFavoriteRemoveUseCase.execute(this.customerId, customerFavoriteRequest)
        }
    }
}
