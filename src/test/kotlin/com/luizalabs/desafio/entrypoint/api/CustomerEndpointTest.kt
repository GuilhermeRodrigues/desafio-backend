package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.test.UnitTest
import com.luizalabs.desafio.core.domain.entity.Customer
import com.luizalabs.desafio.core.domain.entity.Favorite
import com.luizalabs.desafio.core.domain.entity.FavoritesList
import com.luizalabs.desafio.core.interactor.CustomerAddFavoriteInteractor
import com.luizalabs.desafio.core.interactor.CustomerCreateInteractor
import com.luizalabs.desafio.core.interactor.CustomerDeleteInteractor
import com.luizalabs.desafio.core.interactor.CustomerFindAllInteractor
import com.luizalabs.desafio.core.interactor.CustomerFindByIdInteractor
import com.luizalabs.desafio.core.interactor.CustomerFindFavoritesInteractor
import com.luizalabs.desafio.core.interactor.CustomerRemoveFavoriteInteractor
import com.luizalabs.desafio.core.interactor.CustomerUpdateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerFavoriteRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import com.luizalabs.desafio.mapper.toDto
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.test.createMockInstances
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@UnitTest
internal class CustomerEndpointTest {
    @InjectMocks
    private lateinit var customerEndpoint: CustomerEndpoint

    @Mock
    private lateinit var customerCreateInteractor: CustomerCreateInteractor

    @Mock
    private lateinit var customerUpdateInteractor: CustomerUpdateInteractor

    @Mock
    private lateinit var customerDeleteInteractor: CustomerDeleteInteractor

    @Mock
    private lateinit var customerFindByIdInteractor: CustomerFindByIdInteractor

    @Mock
    private lateinit var customerFindAllInteractor: CustomerFindAllInteractor

    @Mock
    private lateinit var customerAddFavoriteInteractor: CustomerAddFavoriteInteractor

    @Mock
    private lateinit var customerFindFavoritesInteractor: CustomerFindFavoritesInteractor

    @Mock
    private lateinit var customerRemoveFavoriteInteractor: CustomerRemoveFavoriteInteractor

    private val customerId = UUID.fromString("a9e4fcbf-0bb4-498a-b652-cb12679f965a")

    private val productId = UUID.fromString("63b1c0af-183c-44f7-b0f1-2bab91aed3f3")

    @Test
    fun `customer endpoint (post)`() {
        val customer = Customer::class.createMockInstance().copy(id = this.customerId)
        val request = CustomerCreateRequest::class.createMockInstance().copy()

        Mockito.`when`(this.customerCreateInteractor.execute(anyObject())).thenReturn(customer)

        val result = this.customerEndpoint.create(request)

        assertEquals(this.customerId, result.id)
        assertNotNull(result.name)
        assertNotNull(result.email)
        assertNotNull(result.createdAt)
    }

    @Test
    fun `customer endpoint (put)`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId,
            updatedAt = LocalDateTime.now()
        )
        val request = CustomerUpdateRequest::class.createMockInstance()
            .copy(
                name = "João da Silva"
            )

        Mockito.`when`(this.customerUpdateInteractor.execute(this.customerId, request.toDto()))
            .thenReturn(
                customer.copy(
                    name = request.name!!
                )
            )

        val result = this.customerEndpoint.update(this.customerId, request)

        assertEquals("João da Silva", result.name)
        assertNotNull(result.updatedAt)
    }

    @Test
    fun `customer endpoint (delete)`() {
        val customer = Customer::class.createMockInstance().copy(
            id = this.customerId,
            deletedAt = LocalDateTime.now()
        )

        Mockito.`when`(this.customerDeleteInteractor.execute(this.customerId))
            .thenReturn(customer)

        val result = this.customerEndpoint.delete(this.customerId)

        assertEquals(customer.id, result.id)
        assertNotNull(result.deletedAt)
    }

    @Test
    fun `customer endpoint (get) - findById`() {
        val customer = Customer::class.createMockInstance().copy(id = this.customerId)

        Mockito.`when`(this.customerFindByIdInteractor.execute(this.customerId))
            .thenReturn(customer)

        val result = this.customerEndpoint.findById(this.customerId)

        assertEquals(customer.id, result.id)
    }

    @Test
    fun `customer endpoint (get) - findAll`() {
        val page = PageRequest.of(1, 3, Sort.by("createdAt").descending())

        val customerPage = PageImpl(Customer::class.createMockInstances(3))

        Mockito.`when`(this.customerFindAllInteractor.execute(page))
            .thenReturn(customerPage)

        val result = this.customerEndpoint.findAll(1, 3)

        assertNotNull(result.body)
    }

    @Test
    fun `customer endpoint (get) - findAll withou params`() {
        val page = PageRequest.of(0, 10, Sort.by("createdAt").descending())

        val customerPage = PageImpl(Customer::class.createMockInstances(3))

        Mockito.`when`(this.customerFindAllInteractor.execute(page))
            .thenReturn(customerPage)

        val result = this.customerEndpoint.findAll()

        assertNotNull(result.body)
    }

    @Test
    fun `customer favorite endpoint (post)`() {
        val product = Product::class.createMockInstance().copy(id = this.productId)
        val customer = Customer::class.createMockInstance().copy(id = this.customerId)
        val favoritesList = FavoritesList::class.createMockInstance().copy(
            customer = customer
        )
        val favorite = Favorite::class.createMockInstance().copy(
            favoritesList = favoritesList,
            product = product
        )
        val request = CustomerFavoriteRequest(productId = product.id)

        Mockito.`when`(this.customerAddFavoriteInteractor.execute(this.customerId, request.toDto()))
            .thenReturn(favorite)

        val result = this.customerEndpoint.addFavorite(this.customerId, request)

        assertEquals(this.customerId, result.customerId)
        assertEquals(product.id, result.favorites.first().id)
    }

    @Test
    fun `customer favorite endpoint (get) - findFavorites`() {
        val customer = Customer::class.createMockInstance().copy(id = this.customerId)
        val favoritesList = FavoritesList::class.createMockInstance().copy(
            customer = customer
        )
        val favorite = Favorite::class.createMockInstance().copy(
            favoritesList = favoritesList
        )

        Mockito.`when`(this.customerFindFavoritesInteractor.execute(this.customerId))
            .thenReturn(listOf(favorite))

        val result = this.customerEndpoint.findFavorites(this.customerId)

        assertEquals(favorite.favoritesList.customer.id, result.customerId)
    }

    @Test
    fun `customer favorite endpoint (delete)`() {
        val product = Product::class.createMockInstance().copy(id = this.productId)
        val customer = Customer::class.createMockInstance().copy(id = this.customerId)
        val favoritesList = FavoritesList::class.createMockInstance().copy(
            customer = customer
        )
        val favorite = Favorite::class.createMockInstance().copy(
            favoritesList = favoritesList,
            product = product
        )

        val request = CustomerFavoriteRequest(productId = product.id)

        Mockito.`when`(this.customerRemoveFavoriteInteractor.execute(this.customerId, request.toDto()))
            .thenReturn(favorite)

        val result = this.customerEndpoint.removeFavorite(this.customerId, request)

        assertEquals(customer.id, result.customerId)
        assertEquals(favorite.product.id, result.favorites.first().id)
    }
}
