package com.luizalabs.desafio.entrypoint.api

import com.fasterxml.jackson.core.type.TypeReference
import com.luizalabs.desafio.EndpointTest
import com.luizalabs.desafio.config.getObjectMapper
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.domain.Favorite
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
import com.luizalabs.desafio.entrypoint.api.response.CustomerResponse
import com.luizalabs.desafio.entrypoint.api.response.FavoriteResponse
import com.luizalabs.desafio.mapper.toCustomerResponse
import com.luizalabs.desafio.mapper.toFavoriteResponse
import com.luizalabs.desafio.provider.api.product.response.Product
import com.luizalabs.desafio.util.test.anyObject
import com.luizalabs.desafio.util.test.createMockInstance
import com.luizalabs.desafio.util.test.createMockInstances
import com.luizalabs.desafio.util.toObject
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class CustomerEndpointIntegrationTest : EndpointTest() {
    @MockBean
    private lateinit var customerCreateInteractor: CustomerCreateInteractor

    @MockBean
    private lateinit var customerUpdateInteractor: CustomerUpdateInteractor

    @MockBean
    private lateinit var customerDeleteInteractor: CustomerDeleteInteractor

    @MockBean
    private lateinit var customerFindByIdInteractor: CustomerFindByIdInteractor

    @MockBean
    private lateinit var customerFindAllInteractor: CustomerFindAllInteractor

    @MockBean
    private lateinit var customerAddFavoriteInteractor: CustomerAddFavoriteInteractor

    @MockBean
    private lateinit var customerFindFavoritesInteractor: CustomerFindFavoritesInteractor

    @MockBean
    private lateinit var customerRemoveFavoriteInteractor: CustomerRemoveFavoriteInteractor

    private val customerId = UUID.fromString("07a4cfd3-c51a-4235-8d3b-b3c3bb7495f6")

    private val customer = Customer::class.createMockInstance()
        .copy(id = customerId)

    @Test
    fun `customer endpoint (post) - cadastrar cliente`() {
        val request = CustomerCreateRequest::class.createMockInstance().copy(
            name = this.customer.name,
            email = this.customer.email
        )

        Mockito.`when`(this.customerCreateInteractor.execute(anyObject())).thenReturn(this.customer)

        this.restAssured()
            .body(request)
            .`when`()
            .post("/v1/customers")
            .then()
            .assertThat()
            .statusCode(201)
            .body(Matchers.containsString(this.customer.id.toString()))
    }

    @Test
    fun `customer endpoint (put) - atualizar cliente`() {
        val newName = "João da Silva"

        val request = CustomerUpdateRequest::class.createMockInstance().copy(
            name = newName,
        )

        Mockito.`when`(this.customerUpdateInteractor.execute(this.customerId, request)).thenReturn(this.customer.copy(name = newName))

        this.restAssured()
            .body(request)
            .`when`()
            .put("/v1/customers/${this.customerId}")
            .then()
            .assertThat()
            .statusCode(200)
            .body(Matchers.containsString(newName))
    }

    @Test
    fun `customer endpoint (delete) - excluir cliente`() {
        Mockito.`when`(this.customerDeleteInteractor.execute(this.customerId)).thenReturn(this.customer)

        this.restAssured()
            .`when`()
            .delete("/v1/customers/${this.customerId}")
            .then()
            .assertThat()
            .statusCode(200)
            .body(Matchers.containsString(this.customer.id.toString()))
    }

    @Test
    fun `customer endpoint (get) - buscar cliente pelo id`() {
        Mockito
            .`when`(this.customerFindByIdInteractor.execute(this.customer.id))
            .thenReturn(this.customer)

        val restAssured = this.restAssured()
            .`when`()
            .get("/v1/customers/${this.customerId}")

        val result = restAssured
            .body
            .asString()
            .toObject(CustomerResponse::class)

        assertEquals(this.customer.toCustomerResponse(), result)

        restAssured
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `customer endpoint (get) - listar os clientes`() {
        val page = PageRequest.of(1, 3, Sort.by("createdAt").descending())

        val customerPage = PageImpl(Customer::class.createMockInstances(3))

        Mockito
            .`when`(this.customerFindAllInteractor.execute(page))
            .thenReturn(customerPage)

        val restAssured = this.restAssured()
            .`when`()
            .queryParam("_page", "1")
            .queryParam("_limit", "3")
            .get("/v1/customers")

        val result = getObjectMapper().readValue(
            restAssured
                .body
                .asString(),
            object : TypeReference<List<CustomerResponse>>() {}
        ).first()

        assertNotNull(result)

        restAssured
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `customer favorite endpoint (post) - adicionar um favorito`() {
        val request = CustomerFavoriteRequest::class.createMockInstance()

        val product = Product::class.createMockInstance().copy(
            id = request.productId
        )

        val favorite = Favorite::class.createMockInstance().copy(
            product = product
        )

        Mockito.`when`(this.customerAddFavoriteInteractor.execute(this.customerId, request)).thenReturn(favorite)

        this.restAssured()
            .body(request)
            .`when`()
            .post("/v1/customers/${this.customerId}/favorites")
            .then()
            .assertThat()
            .statusCode(201)
            .body(Matchers.containsString(request.productId.toString()))
    }

    @Test
    fun `customer favorite endpoint (get) - listar os favoritos do cliente`() {
        val favorite = Favorite::class.createMockInstance()

        Mockito
            .`when`(this.customerFindFavoritesInteractor.execute(this.customer.id))
            .thenReturn(listOf(favorite))

        val restAssured = this.restAssured()
            .`when`()
            .get("/v1/customers/${this.customerId}/favorites")

        val result = restAssured
            .body
            .asString()
            .toObject(FavoriteResponse::class)

        assertEquals(favorite.toFavoriteResponse(listOf(favorite.product)), result)

        restAssured
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `customer favorite endpoint (delete) - remover um favorito`() {
        val request = CustomerFavoriteRequest::class.createMockInstance()

        val product = Product::class.createMockInstance().copy(
            id = request.productId
        )

        val favorite = Favorite::class.createMockInstance().copy(
            product = product
        )

        Mockito.`when`(this.customerRemoveFavoriteInteractor.execute(this.customerId, request)).thenReturn(favorite)

        this.restAssured()
            .body(request)
            .`when`()
            .delete("/v1/customers/${this.customerId}/favorites")
            .then()
            .assertThat()
            .statusCode(200)
            .body(Matchers.containsString(request.productId.toString()))
    }
}
