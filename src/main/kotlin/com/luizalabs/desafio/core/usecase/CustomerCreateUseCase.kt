package com.luizalabs.desafio.core.usecase

import com.luizalabs.desafio.annotation.UseCase
import com.luizalabs.desafio.core.domain.Customer
import com.luizalabs.desafio.core.gateway.CustomerSaveGateway
import com.luizalabs.desafio.core.interactor.CustomerCreateInteractor
import com.luizalabs.desafio.entrypoint.api.request.CustomerCreateRequest
import com.luizalabs.desafio.mapper.toCore

@UseCase
internal class CustomerCreateUseCase(
    private val customerSaveGateway: CustomerSaveGateway
) : CustomerCreateInteractor {
    override fun execute(customerCreateRequest: CustomerCreateRequest): Customer {
        return this.customerSaveGateway.save(customerCreateRequest.toCore())
    }
}
