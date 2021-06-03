package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.dto.CustomerFavoriteDto
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.domain.entity.FavoritesList
import com.luizalabs.desafio.core.exception.CustomerNotFoundException
import com.luizalabs.desafio.core.exception.FavoriteAlreadyAddedException
import com.luizalabs.desafio.core.gateway.CustomerFindByIdGateway
import com.luizalabs.desafio.core.gateway.FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
import com.luizalabs.desafio.core.gateway.FavoriteSaveGateway
import com.luizalabs.desafio.core.gateway.FavoritesListFindByCustomerIdGateway
import com.luizalabs.desafio.core.gateway.FavoritesListSaveGateway
import com.luizalabs.desafio.core.gateway.ProductFindByIdGateway
import com.luizalabs.desafio.provider.api.product.exception.ProductNotFoundException
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import java.util.UUID
import kotlin.test.assertEquals

@UnitTest
internal class CustomerFavoriteAddUseCaseTest {
    @Mock
    private lateinit var customerFindByIdGateway: CustomerFindByIdGateway

    @Mock
    private lateinit var favoritesListFindByCustomerIdGateway: FavoritesListFindByCustomerIdGateway

    @Mock
    private lateinit var favoritesListSaveGateway: FavoritesListSaveGateway

    @Mock
    private lateinit var favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway: FavoriteFindByFavoritesListIdAndDeletedAtIsNullGateway

    @Mock
    private lateinit var favoriteSaveGateway: FavoriteSaveGateway

    @Mock
    private lateinit var productFindByIdGateway: ProductFindByIdGateway

    @InjectMocks
    private lateinit var customerFavoriteAddUseCase: CustomerFavoriteAddUseCase

    private val customerId = UUID.fromString("3c1980ab-ab8a-4bd8-af2b-e47874380209")
    private val favoritesListId = UUID.fromString("fc6ac9ee-85bb-4591-9360-ccc5f606a926")
    private val productId = UUID.fromString("0a3390b2-5fbb-4413-8e64-c1f60bfe0b4e")

    private val customer = Customer::class.createMockInstance().copy(
        id = this.customerId
    )

    private val favoritesList = FavoritesList::class.createMockInstance().copy(
        id = this.favoritesListId,
        customer = customer
    )

    private val product = Product::class.createMockInstance().copy(
        id = this.productId
    )

    private val favorite = Favorite::class.createMockInstance().copy(
        favoritesList = favoritesList,
        product = product
    )

    private fun verifyAllMethodsCalled() {
        Mockito
            .verify(this.customerFindByIdGateway, Mockito.times(1))
            .findById(id = this.customerId)

        Mockito
            .verify(this.favoritesListFindByCustomerIdGateway, Mockito.times(1))
            .findByCustomerId(customerId = this.customerId)

        Mockito
            .verify(this.favoritesListSaveGateway, Mockito.times(1))
            .save(favoritesList = anyObject())

        Mockito
            .verify(this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway, Mockito.times(1))
            .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = this.favoritesListId)

        Mockito
            .verify(this.favoriteSaveGateway, Mockito.times(1))
            .save(favorite = anyObject())

        Mockito
            .verify(this.productFindByIdGateway, Mockito.times(1))
            .findById(id = this.productId)
    }

    @Test
    fun `Adicionar o favorito do cliente com sucesso`() {
        val customerFavoriteDto = CustomerFavoriteDto::class.createMockInstance().copy(
            productId = favorite.product.id
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

        Mockito
            .`when`(this.favoritesListSaveGateway.save(anyObject(FavoritesList::class.java)))
            .thenReturn(favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(listOf())

        Mockito
            .`when`(
                this.productFindByIdGateway
                    .findById(id = favorite.product.id)
            )
            .thenReturn(favorite.product)

        Mockito
            .`when`(this.favoriteSaveGateway.save(anyObject(Favorite::class.java)))
            .thenReturn(favorite)

        val result = this.customerFavoriteAddUseCase.execute(this.customerId, customerFavoriteDto)

        this.verifyAllMethodsCalled()
        assertEquals(result.id, favorite.id)
    }

    @Test
    fun `Adicionar o favorito do cliente com sucesso 2`() {
        val customerFavoriteDto = CustomerFavoriteDto(
            productId = UUID.fromString("23d27011-27d6-4d35-81ab-c3fe198599d7")
        )

        val product = Product::class.createMockInstance().copy(
            id = UUID.fromString("62f98d02-d293-43b0-bf4c-b063af51d3b5")
        )

        val favorite = Favorite::class.createMockInstance().copy(
            favoritesList = favoritesList,
            product = product
        )

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenReturn(customer)

        Mockito
            .`when`(
                this.favoritesListFindByCustomerIdGateway
                    .findByCustomerId(customerId = customer.id)
            )
            .thenReturn(this.favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(listOf(favorite))

        Mockito
            .`when`(
                this.productFindByIdGateway
                    .findById(id = customerFavoriteDto.productId)
            )
            .thenReturn(favorite.product)

        Mockito
            .`when`(this.favoriteSaveGateway.save(anyObject(Favorite::class.java)))
            .thenReturn(favorite)

        val result = this.customerFavoriteAddUseCase.execute(this.customerId, customerFavoriteDto)

        assertEquals(result.id, favorite.id)
    }

    @Test
    fun `Adicionar o favorito do cliente com sucesso 3`() {
        val customerFavoriteDto = CustomerFavoriteDto::class.createMockInstance().copy(
            productId = favorite.product.id
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

        Mockito
            .`when`(this.favoritesListSaveGateway.save(anyObject(FavoritesList::class.java)))
            .thenReturn(favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(null)

        Mockito
            .`when`(
                this.productFindByIdGateway
                    .findById(id = favorite.product.id)
            )
            .thenReturn(favorite.product)

        Mockito
            .`when`(this.favoriteSaveGateway.save(anyObject(Favorite::class.java)))
            .thenReturn(favorite)

        val result = this.customerFavoriteAddUseCase.execute(this.customerId, customerFavoriteDto)

        this.verifyAllMethodsCalled()
        assertEquals(result.id, favorite.id)
    }

    @Test
    fun `Adicionar o favorito do cliente com erro de cliente não encontrado`() {
        val customerFavoriteDto = CustomerFavoriteDto::class.createMockInstance()

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenThrow(CustomerNotFoundException())

        assertThrows<CustomerNotFoundException> {
            this.customerFavoriteAddUseCase.execute(this.customerId, customerFavoriteDto)
        }
    }

    @Test
    fun `Adicionar o favorito do cliente com erro de favorito já adicionado`() {
        val customerFavoriteDto = CustomerFavoriteDto(productId = this.productId)

        Mockito
            .`when`(this.customerFindByIdGateway.findById(id = this.customerId))
            .thenReturn(customer)

        Mockito
            .`when`(
                this.favoritesListFindByCustomerIdGateway
                    .findByCustomerId(customerId = customer.id)
            )
            .thenReturn(null)

        Mockito
            .`when`(this.favoritesListSaveGateway.save(anyObject(FavoritesList::class.java)))
            .thenReturn(favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(listOf(favorite))

        assertThrows<FavoriteAlreadyAddedException> {
            this.customerFavoriteAddUseCase.execute(this.customerId, customerFavoriteDto)
        }
    }

    @Test
    fun `Adicionar o favorito do cliente com erro de produto não encontrado`() {
        val customerFavoriteDto = CustomerFavoriteDto::class.createMockInstance().copy(
            productId = favorite.product.id
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

        Mockito
            .`when`(this.favoritesListSaveGateway.save(anyObject(FavoritesList::class.java)))
            .thenReturn(favoritesList)

        Mockito
            .`when`(
                this.favoriteFindByFavoritesListIdAndDeletedAtIsNullGateway
                    .findByFavoritesListIdAndDeletedAtIsNull(favoritesListId = favoritesList.id)
            )
            .thenReturn(listOf())

        Mockito
            .`when`(
                this.productFindByIdGateway
                    .findById(id = favorite.product.id)
            )
            .thenThrow(ProductNotFoundException())

        assertThrows<ProductNotFoundException> {
            this.customerFavoriteAddUseCase.execute(this.customerId, customerFavoriteDto)
        }
    }
}
