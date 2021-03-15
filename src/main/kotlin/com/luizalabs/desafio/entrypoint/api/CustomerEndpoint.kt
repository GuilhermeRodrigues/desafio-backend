package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.Endpoint
import com.luizalabs.desafio.core.interactor.*
import com.luizalabs.desafio.entrypoint.api.request.CustomerFavoriteRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import com.luizalabs.desafio.entrypoint.api.response.CustomerResponse
import com.luizalabs.desafio.entrypoint.api.response.FavoriteResponse
import com.luizalabs.desafio.mapper.toCustomerResponse
import com.luizalabs.desafio.mapper.toFavoriteResponse
import com.luizalabs.desafio.util.toResponseEntity
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID
import javax.validation.constraints.Max

@Validated
@Endpoint("/v1/customers")
@Api(tags = ["Clientes"], description = "/v1/customers")
class CustomerEndpoint(
    private val customerCreateInteractor: CustomerCreateInteractor,
    private val customerUpdateInteractor: CustomerUpdateInteractor,
    private val customerDeleteInteractor: CustomerDeleteInteractor,
    private val customerFindByIdInteractor: CustomerFindByIdInteractor,
    private val customerFindAllInteractor: CustomerFindAllInteractor,
    private val customerAddFavoriteInteractor: CustomerAddFavoriteInteractor,
    private val customerFindFavoritesInteractor: CustomerFindFavoritesInteractor,
    private val customerRemoveFavoriteInteractor: CustomerRemoveFavoriteInteractor
) {
    @ApiOperation(value = "Criar um cliente")
    @PostMapping
    @ApiResponses(
        value =
        [
            ApiResponse(code = 201, message = "OK"),
            ApiResponse(code = 409, message = "Email já utilizado")
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody(required = true) request: CustomerCreateRequest): CustomerResponse {
        return this.customerCreateInteractor.execute(customerCreateRequest = request).toCustomerResponse()
    }

    @ApiOperation(value = "Atualizar um cliente")
    @PutMapping("/{id}")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Cliente não encontrado"),
            ApiResponse(code = 409, message = "Email já utilizado")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: UUID, @RequestBody(required = true) request: CustomerUpdateRequest): CustomerResponse {
        return this.customerUpdateInteractor.execute(id = id, customerUpdateRequest = request).toCustomerResponse()
    }

    @ApiOperation(value = "Apagar um cliente")
    @DeleteMapping("/{id}")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Cliente não encontrado")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: UUID): CustomerResponse {
        return this.customerDeleteInteractor.execute(id = id).toCustomerResponse()
    }

    @ApiOperation(value = "Consultar um cliente pelo id")
    @GetMapping("/{id}")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Cliente não encontrado")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: UUID): CustomerResponse {
        return this.customerFindByIdInteractor.execute(id).toCustomerResponse()
    }

    @ApiOperation(value = "Listar os clientes")
    @GetMapping
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Página não encontrada")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun findAll(
        @RequestParam(required = false, name = "_page")
        page: Int? = null,

        @RequestParam(required = false, name = "_limit")
        @Max(1000, message = "O valor de limit deve ser menor ou igual a 1000")
        limit: Int? = null
    ): ResponseEntity<List<CustomerResponse>> {
        return this.customerFindAllInteractor
            .execute(PageRequest.of(page ?: 0, limit ?: 10, Sort.by("createdAt").descending()))
            .map { it.toCustomerResponse() }
            .toResponseEntity()
    }

    @ApiOperation(value = "Adicionar um favorito")
    @PostMapping("/{customerId}/favorites")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 201, message = "OK"),
            ApiResponse(code = 404, message = "Cliente não encontrado"),
            ApiResponse(code = 404, message = "Produto não encontrado"),
            ApiResponse(code = 409, message = "Produto já adicionado como favorito")
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun addFavorite(@PathVariable customerId: UUID, @RequestBody(required = true) request: CustomerFavoriteRequest): FavoriteResponse {
        val favorite = this.customerAddFavoriteInteractor.execute(customerId = customerId,customerFavoriteRequest = request)

        return favorite.toFavoriteResponse(listOf(favorite.product))
    }

    @ApiOperation(value = "Listar os favoritos do cliente")
    @GetMapping("/{customerId}/favorites")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Favorito não encontrado")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun findFavorites(@PathVariable customerId: UUID): FavoriteResponse {
        val favorites = this.customerFindFavoritesInteractor
            .execute(customerId)
            
        return favorites.first().toFavoriteResponse(favorites.map { it.product })
    }

    @ApiOperation(value = "Remover um favorito")
    @DeleteMapping("/{customerId}/favorites")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 404, message = "Cliente não encontrado"),
            ApiResponse(code = 404, message = "Favorito não encontrado")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun removeFavorite(@PathVariable customerId: UUID, @RequestBody(required = true) request: CustomerFavoriteRequest): FavoriteResponse {
        val favorite = this.customerRemoveFavoriteInteractor.execute(customerId = customerId,customerFavoriteRequest = request)

        return favorite.toFavoriteResponse(listOf(favorite.product))
    }
}
