package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.domain.entity.FavoritesList
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.FavoriteNotFoundException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals

@UnitTest
internal class CustomerFindFavoritesUseCaseTest {
    @Mock
    private lateinit var customerFindByIdGateway: CustomerFindByIdGateway

    @Mock
    private lateinit var favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway

    @Mock
    private lateinit var favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway

    @InjectMocks
    private lateinit var customerFindFavoritesUseCase: CustomerFindFavoritesUseCase

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
    }

    @Test
    fun `Listar os favoritos do cliente com sucesso`() {
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

        val result = this.customerFindFavoritesUseCase.execute(this.customerId)

        this.verifyAllMethodsCalled()
        assertEquals(result.first().favoritesList.customer.id, customer.id)
        assertEquals(result.first().favoritesList.id, favoritesList.id)
    }

    @Test
    fun `Listar os favoritos do cliente com erro de cliente não encontrado`() {
        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenThrow(CustomerNotFoundException())

        assertThrows<CustomerNotFoundException> {
            this.customerFindFavoritesUseCase.execute(this.customerId)
        }
    }

    @Test
    fun `Listar os favoritos do cliente com erro de favorito não encontrado`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId
        )

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
            this.customerFindFavoritesUseCase.execute(this.customerId)
        }
    }

    @Test
    fun `Listar os favoritos do cliente com erro de favorito não encontrado 2`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId
        )

        val favoritesList = FavoritesList::class.createMockInstance().copy(
            id = this.favoritesListId,
            customer = customer
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
            .thenReturn(null)

        assertThrows<FavoriteNotFoundException> {
            this.customerFindFavoritesUseCase.execute(this.customerId)
        }
    }
}
