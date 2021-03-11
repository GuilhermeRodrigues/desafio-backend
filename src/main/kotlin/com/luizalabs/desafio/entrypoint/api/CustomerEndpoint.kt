package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.Endpoint
import com.luizalabs.desafio.core.interactor.CustomerCreateInteractor
import com.luizalabs.desafio.core.interactor.CustomerFindByIdInteractor
import com.luizalabs.desafio.core.interactor.CustomerUpdateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import com.luizalabs.desafio.entrypoint.api.response.CustomerResponse
import com.luizalabs.desafio.mapper.toCustomerResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Validated
@Endpoint("/v1/customers")
@Api(tags = ["Clientes"], description = "/v1/customers")
class CustomerEndpoint(
    private val customerCreateInteractor: CustomerCreateInteractor,
    private val customerUpdateInteractor: CustomerUpdateInteractor,
    private val customerFindByIdInteractor: CustomerFindByIdInteractor
) {
    @ApiOperation(value = "Criar um cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody(required = true) request: CustomerCreateRequest): CustomerResponse {
        return this.customerCreateInteractor.execute(customerCreateRequest = request).toCustomerResponse()
    }

    @ApiOperation(value = "Atualizar um cliente")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: UUID, @RequestBody(required = true) request: CustomerUpdateRequest): CustomerResponse {
        return this.customerUpdateInteractor.execute(id = id, customerUpdateRequest = request).toCustomerResponse()
    }

    @GetMapping("/{id}")
    @ApiResponses(
        value =
        [
            ApiResponse(code = 200, message = "Dados do cliente"),
            ApiResponse(code = 404, message = "Cliente não encontrado")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: UUID): CustomerResponse {
        return this.customerFindByIdInteractor.execute(id).toCustomerResponse()
    }
}
