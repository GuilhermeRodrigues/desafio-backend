package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.component.Endpoint
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
import com.luizalabs.desafio.mapper.toDto
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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID
import javax.validation.Valid
import javax.validation.constraints.Max

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
    @ApiOperation(value = "Cadastrar um cliente")
    @PostMapping
    @ApiResponses(
        value =
            [
                ApiResponse(code = 201, message = "OK"),
                ApiResponse(code = 409, message = "Email já utilizado")
            ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: CustomerCreateRequest): CustomerResponse {
        return this.customerCreateInteractor.execute(customerCreateDto = request.toDto()).toCustomerResponse()
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
    fun update(@PathVariable id: UUID, @RequestBody @Valid request: CustomerUpdateRequest): CustomerResponse {
        return this.customerUpdateInteractor.execute(id = id, customerUpdateDto = request.toDto()).toCustomerResponse()
    }

    @ApiOperation(value = "Excluir um cliente")
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

    @ApiOperation(value = "Buscar cliente pelo id")
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
    fun addFavorite(@PathVariable customerId: UUID, @RequestBody @Valid request: CustomerFavoriteRequest): FavoriteResponse {
        val favorite = this.customerAddFavoriteInteractor.execute(customerId = customerId, customerFavoriteDto = request.toDto())

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
    fun removeFavorite(@PathVariable customerId: UUID, @RequestBody @Valid request: CustomerFavoriteRequest): FavoriteResponse {
        val favorite = this.customerRemoveFavoriteInteractor.execute(customerId = customerId, customerFavoriteDto = request.toDto())

        return favorite.toFavoriteResponse(listOf(favorite.product))
    }
}
