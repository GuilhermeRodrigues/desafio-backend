package com.luizalabs.desafio.entrypoint.api

import com.luizalabs.desafio.annotation.Endpoint
import com.luizalabs.desafio.core.interactor.CustomerCreateInteractor
import com.luizalabs.desafio.core.interactor.CustomerUpdateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.entrypoint.api.request.CustomerUpdateRequest
import com.luizalabs.desafio.entrypoint.api.response.CustomerResponse
import com.luizalabs.desafio.mapper.toCustomerResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Validated
@Endpoint("/v1/customers")
@Api(tags = ["Clientes"], description = "/v1/customers")
class CustomerEndpoint(
    private val customerCreateInteractor: CustomerCreateInteractor,
    private val customerUpdateInteractor: CustomerUpdateInteractor
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
}
